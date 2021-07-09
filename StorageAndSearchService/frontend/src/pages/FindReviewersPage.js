import React, { useState, useEffect } from "react";
import { useLocation, useHistory } from "react-router-dom";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import CardDeck from "react-bootstrap/CardDeck";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Form from "react-bootstrap/Form";
import Pagination from "react-bootstrap/Pagination";

import queryString from "query-string";
import { BookService } from "../services/BookService";

const FindReviewersPage = () => {
  const [reviewers, setReviewers] = useState([]);
  const [pagination, setPagination] = useState({
    totalPages: undefined,
    page: 0,
    first: undefined,
    last: undefined,
    size: 1,
  });
  const [search, setSearch] = useState({
    title: undefined,
    author: undefined,
    contentExcerpt: undefined,
    genres: "",
    query: undefined,
  });
  const [clickedPage, setClickedPage] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  let location = useLocation();
  let history = useHistory();

  useEffect(() => {
    let currentUrlParams = queryString.parse(location.search);
    setSearch({ ...search, ...currentUrlParams });
    setClickedPage(!clickedPage);
  }, []);

  useEffect(() => {
    let currentUrlParams = queryString.parse(location.search);

    async function findReviewers() {
      const response = await BookService.findReviewers(currentUrlParams);
      console.log(response.data);
      setReviewers(response.data.content);
      setPagination({
        totalPages: response.data.totalPages,
        page: response.data.number,
        first: response.data.first,
        last: response.data.last,
        size: response.data.size,
      });
      setIsLoading(false);
    }
    findReviewers();
  }, [clickedPage]);

  const handlePageChange = (change) => {
    setPagination({ ...pagination, ...change });

    let currentUrlParams = queryString.parse(location.search);
    if (Number.isInteger(change.page)) {
      currentUrlParams = {
        ...currentUrlParams,
        page: change.page,
        size: pagination.size,
      };
    } else if (change.size) {
      currentUrlParams = {
        ...currentUrlParams,
        page: 0,
        size: change.size,
      };
    }

    console.log(currentUrlParams);

    history.push(
      location.pathname +
        "?" +
        queryString.stringify(currentUrlParams) +
        location.hash
    );

    setClickedPage(!clickedPage);
  };

  return (
    <Container>
      <Row
        style={{ marginBottom: 40 }}
        className="text-center d-flex justify-content-center"
      >
        {isLoading && (
          <Col style={{ marginTop: 20 }}>
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
        {!isLoading &&
          reviewers &&
          reviewers.length > 0 &&
          reviewers.map((item) => (
            <Col xs={3} key={item.id}>
              <CardDeck style={{ marginTop: 20 }}>
                <Card>
                  <Card.Body>
                    <Card.Title>{item.content.fullName}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                      {item.content.email}
                    </Card.Subtitle>
                    <Card.Text>
                      <ul>
                        {item.content.genresInterestedInBetaReading &&
                          item.content.genresInterestedInBetaReading.map(
                            (genre) => <li>{genre}</li>
                          )}
                      </ul>
                    </Card.Text>
                  </Card.Body>
                  <Card.Footer>
                    <Button
                      variant="success"
                      onClick={() => alert(`Beta reader chosen: ${item.id}`)}
                      block
                    >
                      Pick beta reader
                    </Button>
                  </Card.Footer>
                </Card>
              </CardDeck>
            </Col>
          ))}
      </Row>
      {pagination && pagination.totalPages > 1 && (
        <Row>
          <Col
            className="text-center d-flex justify-content-center"
            md={{ span: 6, offset: 3 }}
          >
            <Pagination size="lg">
              {!pagination.first && (
                <Pagination.First
                  onClick={() => {
                    handlePageChange({ page: 0 });
                  }}
                />
              )}
              {pagination.page > 0 && (
                <Pagination.Prev
                  onClick={() => {
                    handlePageChange({ page: pagination.page - 1 });
                  }}
                />
              )}
              <Pagination.Item>{pagination.page}</Pagination.Item>
              {pagination.page < pagination.totalPages - 1 && (
                <Pagination.Next
                  onClick={() => {
                    handlePageChange({ page: pagination.page + 1 });
                  }}
                />
              )}
              {!pagination.last && (
                <Pagination.Last
                  onClick={() => {
                    handlePageChange({ page: pagination.totalPages - 1 });
                  }}
                />
              )}
            </Pagination>
            <Form style={{ marginLeft: "5vw", marginTop: "2vh" }}>
              <Form.Group inline controlId="exampleForm.ControlSelect1">
                <Form.Row>
                  <Form.Label inline>Page size</Form.Label>
                  <Col>
                    <Form.Control
                      style={{ marginTop: "-1vh" }}
                      inline
                      as="select"
                      onChange={(e) =>
                        handlePageChange({ size: e.target.value })
                      }
                    >
                      <option>1</option>
                      <option>5</option>
                      <option>10</option>
                      <option>20</option>
                      <option>50</option>
                    </Form.Control>
                  </Col>
                </Form.Row>
              </Form.Group>
            </Form>
          </Col>
        </Row>
      )}
    </Container>
  );
};

export default FindReviewersPage;
