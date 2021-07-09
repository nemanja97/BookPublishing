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
import { _buyMembership } from '../services/InvoiceService';
import { _getPlans } from '../services/InvoiceService';
import { _getMembershipStatus } from '../services/BookService'

const MembershipPage = (props) => {
    const [hasMembership, setHasMembership] = useState(false);
    const [plans, setPlans] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function fetchMemberships() {
            const response = await _getPlans();
            console.log(response.data);
            setPlans(response.data.listOfPlans);
            setIsLoading(false);
        }

        fetchMemberships();
    }, []);


    useEffect(() => {
        async function getMembershipStatus() {
            const response = await _getMembershipStatus();
            setHasMembership(response.data);
            console.log(response);
        }

        getMembershipStatus();
    }, []);

    const buyMembership = async (e) => {
        console.log(e);
        const response = await _buyMembership(e);
        console.log(response);
        openInNewTab(response.data);
        try {

        } catch (error) {
            console.error(error);
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
                    {!isLoading && plans.length > 0 && (
                        <CardDeck style={{ marginTop: 40 }}>
                            {plans.map((item) => (
                                <Col xs={12} key={item.id} style={{ marginBottom: 40 }}>
                                    <Card>
                                    <Card.Body style={{ height: '7vw' }}>
                                            <Card.Title>{item.name}</Card.Title>
                                            <Card.Subtitle>{item.description}</Card.Subtitle>
                                            <Card.Subtitle>{item.frequency}</Card.Subtitle>
                                        </Card.Body>
                                        <Card.Footer>
                                            Price: {item.amount} {item.currency}
                                            <Card.Subtitle>First time: {item.amountStart}</Card.Subtitle>
                                        </Card.Footer>
                                        {hasMembership && (
                                            <Button block disabled>
                                                Already Member
                                            </Button>
                                        )}
                                        {!hasMembership && (
                                            <Button block onClick={() => buyMembership(item)}>
                                                Buy membership
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

export default MembershipPage;
