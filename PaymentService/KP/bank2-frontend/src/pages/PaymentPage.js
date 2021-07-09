import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Card from 'react-credit-cards';

import Spinner from 'react-bootstrap/Spinner';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { formatCreditCardNumber, formatCVC, formatExpirationDate } from '../utils/payment-utils.js';
import 'react-credit-cards/es/styles-compiled.css';
import axios from 'axios';
import AlertComponent from '../shared/AlertComponent.js';

function PaymentPage() {
	const [creditCard, setCreditCard] = useState({
		number: '',
		name: '',
		expiry: '',
		cvc: '',
		focused: '',
	});
	const [alert, setAlert] = useState(null);
	const [isLoading, setIsLoading] = useState(false);
	const location = useLocation();

	useEffect(() => {
		console.log(location);
	}, []);

	const handleInputFocus = ({ target }) => {
		setCreditCard({
			...creditCard,
			focused: target.name,
		});
	};

	const handleInputChange = ({ target }) => {
		if (target.name === 'number') {
			target.value = formatCreditCardNumber(target.value);
		} else if (target.name === 'expiry') {
			target.value = formatExpirationDate(target.value);
		} else if (target.name === 'cvc') {
			target.value = formatCVC(target.value);
		}

		setCreditCard({ ...creditCard, [target.name]: target.value });
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		setIsLoading(true);
		const dto = {
			panNumber: creditCard.number.replaceAll(' ', ''),
			cardHolderName: creditCard.name,
			expiratoryDate: creditCard.expiry,
			securityCode: creditCard.cvc,
		};
		console.log(dto);

		try {
			const res = await axios.put(
				`${process.env.REACT_APP_BANK_API}/payment/invoice${location.pathname.substring(
					location.pathname.lastIndexOf('/')
				)}`,
				dto
			);
			setIsLoading(false);
			console.log(res);
			openInNewTab(res.data.redirectUrl);
		} catch (ex) {
			setIsLoading(false);
			console.error(ex.response);

			// DTO Constraint violation
			if (ex.response.data.violations) {
				setAlert({
					heading: 'Invalid credit card information',
					violations: ex.response.data.violations,
				});
				return;
			}

			// Entity not found violation
			if (ex.response.status === 404 && ex.response.data) {
				if (ex.response.data == 'Invoice not found')
					setAlert({
						heading: 'Did you get here from a valid referral link?',
						violations: [{ message: ex.response.data }],
					});
				else
					setAlert({
						heading: "We can't find any such account. Double check all your info.",
						violations: [{ message: ex.response.data }],
					});
				return;
			}

			//
			if (ex.response.status === 400) {
				if (ex.response.data == 'Invoice already paid') {
					setAlert({
						heading: 'Did you get here from a valid referral link?',
						violations: [{ message: ex.response.data }],
					});
					return;
				}

				setAlert({
					heading: 'We found your account, but there have been some errors:',
					violations: [{ message: ex.response.data }],
				});
			}
		}
	};

	const openInNewTab = (url) => {
		console.log('here');
		const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
		if (newWindow) newWindow.opener = null;
	};

	return (
		<>
			<Container>
				{isLoading && (
					<Row style={{ marginTop: 20 }}>
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
					</Row>
				)}
				{!isLoading && (
					<>
						<Row style={{ marginTop: 20 }}>
							<Col>
								{alert && <AlertComponent alert={alert} setAlert={setAlert} />}
								<div id="card-wrapper">
									<div key="Payment">
										<Card
											number={creditCard.number}
											name={creditCard.name}
											expiry={creditCard.expiry}
											cvc={creditCard.cvc}
											focused={creditCard.focused}
										/>
									</div>
								</div>
							</Col>
						</Row>
						<Row style={{ marginTop: 15 }}>
							<Col>
								<form onSubmit={handleSubmit}>
									<div className="form-group">
										<input
											type="tel"
											name="number"
											className="form-control"
											placeholder="Card Number"
											pattern="\d{4} \d{4} \d{4} \d{4}"
											required
											onChange={handleInputChange}
											onFocus={handleInputFocus}
										/>
									</div>
									<div className="form-group">
										<input
											type="text"
											name="name"
											className="form-control"
											placeholder="Name"
											pattern="([a-zA-Z ])+"
											required
											onChange={handleInputChange}
											onFocus={handleInputFocus}
										/>
									</div>
									<div className="row">
										<div className="col-6">
											<input
												type="tel"
												name="expiry"
												className="form-control"
												placeholder="Valid Thru"
												pattern="(0[1-9]|1[0-2])\/[0-9]{2}"
												required
												onChange={handleInputChange}
												onFocus={handleInputFocus}
											/>
										</div>
										<div className="col-6">
											<input
												type="tel"
												name="cvc"
												className="form-control"
												placeholder="CVC"
												pattern="\d{3,4}"
												required
												onChange={handleInputChange}
												onFocus={handleInputFocus}
											/>
										</div>
									</div>
									<input type="hidden" name="issuer" value={creditCard.issuer} />
									<div className="form-actions" style={{ marginTop: 15 }}>
										<button className="btn btn-primary btn-block">Pay</button>
									</div>
								</form>
							</Col>
						</Row>
					</>
				)}
			</Container>
		</>
	);
}

export default PaymentPage;
