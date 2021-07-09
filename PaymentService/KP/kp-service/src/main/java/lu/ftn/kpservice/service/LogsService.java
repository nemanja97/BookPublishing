package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.entity.BankLog;
import lu.ftn.kpservice.model.entity.BitcoinLog;
import lu.ftn.kpservice.model.entity.PaypalLog;

import java.util.Date;
import java.util.List;

public interface LogsService {

    BankLog saveBankLog(BankLog log);

    PaypalLog savePaypalLog(PaypalLog log);

    List<PaypalLog> getAllPaypalLogs();

    PaypalLog getPaypalLog(String id);

    List<PaypalLog> getAllPaypalLogsWhereStatus(String status);

    List<PaypalLog> getPaypalLogsBetween(Date from, Date to);

    BitcoinLog saveBitcoinLog(BitcoinLog log);

    List<BitcoinLog> getAllBitcoinLogs();

    BitcoinLog getBitcoinLog(String id);

    List<BitcoinLog> getAllBitcoinLogsWhereStatus(String status);

    List<BitcoinLog> getBitcoinLogsBetween(Date from, Date to);

}
