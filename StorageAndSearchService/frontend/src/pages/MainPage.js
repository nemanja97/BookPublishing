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
import ListGroup from "react-bootstrap/ListGroup";
import Tab from "react-bootstrap/Tab";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Popover from "react-bootstrap/Popover";

import queryString from "query-string";
import { BookService } from "../services/BookService";
import genres from "../model/enums/genres";

const MainPage = () => {
  const [books, setBooks] = useState([]);
  const [pagination, setPagination] = useState({
    totalPages: undefined,
    page: 0,
    first: undefined,
    last: undefined,
    size: 1,
  });
  const [searchType, setSeachType] = useState("bar");
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

    async function searchBooks() {
      const response = await BookService.search(currentUrlParams);
      console.log(response.data);
      setBooks(response.data.content);
      setPagination({
        totalPages: response.data.totalPages,
        page: response.data.number,
        first: response.data.first,
        last: response.data.last,
        size: response.data.size,
      });
      setIsLoading(false);
    }
    searchBooks();
  }, [clickedPage]);

  const handleSearchInputChange = ({ target }) => {
    setSearch({ ...search, [target.name]: target.value });
  };

  const handleBookDownload = (bookId, fileName) => {
    async function searchBooks() {
      try {
        const response = await BookService.download(bookId);
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileName); //or any other extension
        document.body.appendChild(link);
        link.click();
      } catch (error) {
        alert("Could not download book");
      }
    }
    searchBooks();
  };

  const handleSearchMultiselectInputChange = ({ target }) => {
    const value = Array.from(target.selectedOptions, (option) => option.value);
    setSearch({ ...search, [target.name]: value.join() });
  };

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

  const handleSearchSubmit = (e) => {
    e.preventDefault();

    let currentUrlParams = queryString.parse(location.search);

    if (searchType == "fields")
      currentUrlParams = {
        ...currentUrlParams,
        title: search.title,
        author: search.author,
        contentExcerpt: search.contentExcerpt,
        genres: search.genres,
        query: undefined,
      };

    if (searchType == "bar")
      currentUrlParams = {
        ...currentUrlParams,
        title: undefined,
        author: undefined,
        contentExcerpt: undefined,
        genres: undefined,
        query: search.query,
      };

    history.push(
      location.pathname +
        "?" +
        queryString.stringify(currentUrlParams) +
        location.hash
    );
    setClickedPage(!clickedPage);
  };

  const popover = (
    <Popover id="popover-basic">
      <Popover.Title as="h3">Advanced search</Popover.Title>
      <Popover.Content>
        Did you know your search can be even more <strong>amazing</strong> ?
        <br />
        You can search the following fields: title, writer.fullName, genres,
        content, price.
        <br />
        You can specify specific fields to search in the query syntax:
        <ul>
          <li>
            where the title field contains "Sestre": <em>title:Sestre</em>
          </li>
          <li>
            where the writer.fullName field contains "Hannah" or "James":{" "}
            <em>writer.fullName:(Hannah or James)</em>
          </li>
          <li>
            where the content field contains the exact phrase "John Smith":{" "}
            <em>content:"John Smith"</em>
          </li>
          <li>
            where the price field is between 10 to a 100, anything from 10 or up
            till 100:
            <br /> <em>price:[10 to 100]</em>
            <br />
            <em>price:[10 to *]</em>
            <br />
            <em>price:[* to 100]</em>
            <br />
          </li>
        </ul>
        You can even combine the search terms, such as:
        <ul>
          <li>
            where the either the content or the title have "this" and "that":{" "}
            <em>(content:this OR name:this) AND (content:that OR name:that)</em>
          </li>
        </ul>
      </Popover.Content>
    </Popover>
  );

  return (
    <Container>
      <Row>
        <Col>
          <Tab.Container
            id="list-group-tabs-example"
            style={{ marginTop: 20 }}
            activeKey={`#${searchType}`}
          >
            <Row>
              <Col xs={1}>
                <ListGroup style={{ marginTop: 20 }}>
                  <ListGroup.Item
                    action
                    href="#bar"
                    onClick={() => {
                      setSeachType("bar");
                    }}
                    size="xs"
                  >
                    <span className="material-icons">search</span>
                  </ListGroup.Item>
                  <ListGroup.Item
                    action
                    href="#fields"
                    onClick={() => {
                      setSeachType("fields");
                    }}
                    size="xs"
                  >
                    <span className="material-icons">manage_search</span>
                  </ListGroup.Item>
                </ListGroup>
              </Col>
              <Col xs={11}>
                <Tab.Content style={{ marginTop: 20 }}>
                  <Tab.Pane eventKey="#bar">
                    <Form>
                      <Form.Row>
                        <Col xs={11}>
                          <Form.Control
                            name="query"
                            placeholder="Title / Author / Content / Genres"
                            onChange={handleSearchInputChange}
                            value={search.query}
                          />
                        </Col>
                        <Col xs={1}>
                          <OverlayTrigger
                            trigger="click"
                            placement="left"
                            overlay={popover}
                          >
                            <Button block variant="success">
                              <span class="material-icons">help</span>
                            </Button>
                          </OverlayTrigger>
                        </Col>
                      </Form.Row>
                      <Form.Row style={{ marginTop: 10 }}>
                        <Col>
                          <Button
                            variant="primary"
                            onClick={handleSearchSubmit}
                            block
                            type="submit"
                          >
                            Submit
                          </Button>
                        </Col>
                      </Form.Row>
                    </Form>
                  </Tab.Pane>
                  <Tab.Pane eventKey="#fields">
                    <Form>
                      <Form.Row style={{ marginTop: 10 }}>
                        <Col xs={6}>
                          <Form.Control
                            placeholder="Title"
                            onChange={handleSearchInputChange}
                            name="title"
                            value={search.title}
                          />
                        </Col>
                        <Col xs={6}>
                          <Form.Control
                            placeholder="Author"
                            onChange={handleSearchInputChange}
                            name="author"
                            value={search.author}
                          />
                        </Col>
                      </Form.Row>
                      <Form.Row style={{ marginTop: 10 }}>
                        <Col xs={6}>
                          <Form.Control
                            placeholder="Content excerpt"
                            onChange={handleSearchInputChange}
                            name="contentExcerpt"
                            value={search.contentExcerpt}
                          />
                        </Col>
                        <Col xs={6}>
                          <Form.Control
                            placeholder="Genres"
                            onChange={handleSearchMultiselectInputChange}
                            name="genres"
                            as="select"
                            multiple
                            htmlSize="3"
                            value={search.genres.split(",")}
                          >
                            {genres.map((genre) => (
                              <option>{genre}</option>
                            ))}
                          </Form.Control>
                        </Col>
                      </Form.Row>
                      <Form.Row style={{ marginTop: 10 }}>
                        <Col xs={12}>
                          <Button
                            variant="primary"
                            onClick={handleSearchSubmit}
                            block
                            type="submit"
                          >
                            Submit
                          </Button>
                        </Col>
                      </Form.Row>
                    </Form>
                  </Tab.Pane>
                </Tab.Content>
              </Col>
            </Row>
          </Tab.Container>
        </Col>
      </Row>
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
          books &&
          books.length > 0 &&
          books.map((item) => (
            <Col xs={4} key={item.id}>
              <CardDeck style={{ marginTop: 20 }}>
                <Card>
                  <Card.Img
                    style={{
                      width: "100%",
                      height: "25vw",
                      objectFit: "cover",
                    }}
                    variant="top"
                    src={item.content.base64image}
                  />
                  <Card.Body>
                    <Card.Title>{item.content.title}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                      {item.content.writer.fullName}
                      <br />
                      Genres:
                      {item.content.genres.map((genre) => ` ${genre} `)}
                    </Card.Subtitle>
                    {item.highlightFields && (
                      <Card.Text
                        dangerouslySetInnerHTML={{
                          __html: item.highlightFields.content,
                        }}
                      ></Card.Text>
                    )}
                  </Card.Body>
                  <Card.Footer>
                    {item.content.openAccess && (
                      <Button
                        variant="success"
                        onClick={() =>
                          handleBookDownload(
                            item.id,
                            `${item.content.title}.pdf`
                          )
                        }
                        block
                      >
                        Open access
                      </Button>
                    )}
                    {!item.content.openAccess && (
                      <Button
                        variant="primary"
                        onClick={() => alert(`Buying book: ${item.id}`)}
                        block
                      >
                        {item.content.price} {item.content.currency}
                      </Button>
                    )}
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

export default MainPage;
