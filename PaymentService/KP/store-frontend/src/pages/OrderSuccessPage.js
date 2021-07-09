import React from 'react';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';

const OrderSuccessPage = () => {
	return (
		<>
			<Container>
				<Row>
					<Col style={{ marginTop: 80 }} md={{ span: 6, offset: 3 }}>
						<Image src={`${process.env.PUBLIC_URL}/purchaseSuccess.png`} fluid />
						<Card border="light" className="text-center" style={{ marginTop: 20 }}>
							<Card.Body>
								{' '}
								<Card.Title style={{ color: 'green', fontSize: 45 }}>
									Thank you for your purchase!
								</Card.Title>
								<Card.Text style={{ fontStyle: 'italic', fontSize: 20 }}>
									The item have been added to your account.
								</Card.Text>
							</Card.Body>
						</Card>
					</Col>
				</Row>
			</Container>
		</>
	);
};

export default OrderSuccessPage;
