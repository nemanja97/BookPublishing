package lu.ftn.luaccountingservice.service.implementation;

import lu.ftn.luaccountingservice.model.dto.MembershipDTO;
import lu.ftn.luaccountingservice.model.entity.*;
import lu.ftn.luaccountingservice.model.enums.MembershipFrequency;
import lu.ftn.luaccountingservice.model.enums.TransactionType;
import lu.ftn.luaccountingservice.repository.InvoiceRepository;
import lu.ftn.luaccountingservice.repository.MembershipRepository;
import lu.ftn.luaccountingservice.repository.TransactionRepository;
import lu.ftn.luaccountingservice.service.BookService;
import lu.ftn.luaccountingservice.service.InvoiceService;
import lu.ftn.luaccountingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    MembershipRepository membershipRepository;

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public void markInvoicePaymentAsValid(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type,
                                          String status, Date utcTransactionTime) throws Exception {
        Invoice invoice = updateInvoiceWithTransaction(id, serviceIssuedId, storeIssuedInvoiceId, type, status, utcTransactionTime);
        switch (status) {
            case "success":
            case "paid":
                if (invoice.getBooks() != null && invoice.getBooks().size() > 0) {
                    setBooksAsOwned(invoice);
                } else if (invoice.getMembership() != null) {
                    setMembership(invoice);
                }
                break;
        }
    }

    @Override
    public void markInvoicePaymentAsInvalid(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type,
                                            String status, Date utcTransactionTime) throws Exception {
        Invoice invoice = updateInvoiceWithTransaction(id, serviceIssuedId, storeIssuedInvoiceId, type, status, utcTransactionTime);
    }

    private void setBooksAsOwned(Invoice invoice) throws Exception {
        User user = invoice.getPayer();
        if (user.getOwnedBooks().addAll(invoice.getBooks())) {
            userService.save(user);
        } else {
            throw new Exception("Unable to add books");
        }
    }

    private void setMembership(Invoice invoice) throws Exception {
        User user = invoice.getPayer();
        if (invoice.getMembership() != null) {
            invoice.getMembership().setStartingDate(new Date());
            user.setMemberships(new HashSet<>());
            user.getMemberships().add(invoice.getMembership());
            userService.save(user);
        } else {
            throw new Exception("Unable to add membership");
        }
    }

    private Invoice updateInvoiceWithTransaction(String id, String serviceIssuedId, String storeIssuedInvoiceId, TransactionType type, String status, Date utcTransactionTime) throws Exception {
        Invoice invoice = findById(storeIssuedInvoiceId);
        if (invoice == null)
            throw new Exception("Invoice not found");

        Transaction transaction = new Transaction(id, serviceIssuedId, invoice, type, status, utcTransactionTime);
        transaction = transactionRepository.save(transaction);

        invoice.setTransaction(transaction);
        invoice = save(invoice);
        return invoice;
    }

    @Override
    public Invoice createInvoice(List<String> bookIds, String userId) {
        User user = userService.findUserById(userId);
        List<Book> books = bookService.findAll().stream().filter(b -> bookIds.contains(b.getId())).collect(Collectors.toList());

        Invoice invoice = new Invoice(books.stream().map(Book::getPrice).reduce(BigDecimal.valueOf(0), BigDecimal::add), "EUR", books, user);
        return save(invoice);
    }

    @Override
    public Invoice createInvoiceForMembership(MembershipDTO dto, String userId) {
        User user = userService.findUserById(userId);
        Membership membership = new Membership(new Date(), MembershipFrequency.valueOf(dto.getFrequency()), dto.getMembershipName(), dto.getPrice(), dto.getCurrency());
        membership = membershipRepository.save(membership);
        Invoice invoice = new Invoice(dto.getPrice(), dto.getCurrency(), membership, user);
        return save(invoice);
    }

    @Override
    public void setMembershipAsExpired() {
        List<Membership> membershipList = membershipRepository.findAll();

        for (Membership membership: membershipList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(membership.getStartingDate());
            switch (membership.getFrequency()) {
                case DAY: calendar.add(Calendar.DATE, 1);break;
                case WEEK: calendar.add(Calendar.DATE, 7); break;
                case MONTH: calendar.add(Calendar.MONTH, 1); break;
                case YEAR: calendar.add(Calendar.YEAR, 1); break;
            }
            if (calendar.getTime().before(new Date())) {
                membership.setExpired(true);
                membershipRepository.save(membership);
            }
        }
    }
}
