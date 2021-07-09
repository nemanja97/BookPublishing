import React, { useState, useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import CardDeck from 'react-bootstrap/CardDeck';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';

import { BookService } from '../services/BookService';
import UserNavbar from '../shared/UserNavbar';
import { InvoiceService } from '../services/InvoiceService';

const StorePage = (props) => {
	const [itemsInCart, setCartItems] = useState([]);
	const [books, setBooks] = useState([]);
	const [showAlert, setShowAlert] = useState(false);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		async function fetchBooks() {
			const response = await BookService.getAll();
			console.log(response.data);
			setBooks(response.data.books);
			setIsLoading(false);
		}

		fetchBooks();
	}, []);

	const itemIsInCart = (id) => {
		return itemsInCart.includes(id);
	};

	const handleCartItemAddition = (id) => {
		console.log('add');
		console.log(id);
		const itemsInCartNew = itemsInCart.concat(id);
		setCartItems(itemsInCartNew);
		console.log(itemsInCart);
	};

	const handleCartItemRemoval = (id) => {
		console.log('removal');
		console.log(id);
		setCartItems(itemsInCart.filter((bookId) => bookId != id));
		console.log(itemsInCart);
	};

	const checkoutCardItems = async () => {
		const dto = {
			bookIds: itemsInCart,
		};
		setIsLoading(true);
		setCartItems([]);

		const response = await InvoiceService.createNewInvoice(dto);
		setIsLoading(false);
		if (response.status === 200) {
			openInNewTab(response.data);
		} else {
			alert('There way an processing your order');
		}
		console.log(response);
	};

	const openInNewTab = (url) => {
		console.log('here');
		const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
		if (newWindow) newWindow.opener = null;
	};

	return (
		<>
			<UserNavbar setCartItems={setCartItems} itemsInCart={itemsInCart} checkoutCardItems={checkoutCardItems} />
			<Container>
				<Row>
					{isLoading && (
						<Col className="text-center" style={{ marginTop: 80 }} md={{ span: 6, offset: 3 }}>
							<Spinner
								animation="border"
								style={{ height: 500, width: 500 }}
								variant="primary"
								role="status"
							>
								<span className="sr-only">Loading...</span>
							</Spinner>
						</Col>
					)}
					{!isLoading && books.length > 0 && (
						<CardDeck style={{ marginTop: 40 }}>
							{books.map((item) => (
								<Col xs={4} key={item.id} style={{ marginBottom: 40 }}>
									<Card>
										<Card.Img
											style={{ width: '100%', height: '25vw', objectFit: 'cover' }}
											variant="top"
											src={item.pictureUrl}
										/>
										<Card.Body style={{ height: '7vw' }}>
											<Card.Title>{item.title}</Card.Title>
										</Card.Body>
										<Card.Footer>
											Price: {item.price} {item.currency}
										</Card.Footer>
										{item.owned && (
											<Button block disabled>
												Already owned
											</Button>
										)}
										{!item.owned && itemIsInCart(item.id) && (
											<Button block onClick={() => handleCartItemRemoval(item.id)}>
												Remove from Cart
											</Button>
										)}
										{!item.owned && !itemIsInCart(item.id) && (
											<Button block onClick={() => handleCartItemAddition(item.id)}>
												Add to Cart
											</Button>
										)}
									</Card>
								</Col>
							))}
						</CardDeck>
					)}
				</Row>
			</Container>
		</>
	);
};

export default StorePage;
