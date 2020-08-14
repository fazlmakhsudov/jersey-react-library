import React, { useState, useEffect } from 'react';
import ImageHolder from './image-holder.js';
import axios from 'axios';
import { Row, Col, ButtonGroup, Dropdown, Modal, Form, Button, Accordion, Card } from 'react-bootstrap';


function Book(props) {
  let url = props.url + "/book";
  let setData = props.setData;
  let setBooks = props.setBooks;
  let books = props.books;
  let authorityFlag = props.authorityFlag;
  let authors = props.authors;

  const [flag, setFlag] = useState(true);
  const [bookId, setBookId] = useState(0);
  const [showFlag, setShowFlag] = useState(false);
  const [searchText, setSearchText] = useState('');
  const [name, setName] = useState('');
  const [publishDate, setPublishDate] = useState('');
  const [updateFlag, setUpdateFlag] = useState(false);
  const [createFlag, setCreateFlag] = useState(false);
  const [deleteFlag, setDeleteFlag] = useState(false);
  const [authorName, setAuthorName] = useState('');
  const [chooseUpdateFlag, setChooseUpdateFlag] = useState(false);
  const [chooseModalBodyFlag, setChooseModalBodyFlag] = useState(false);
  const [chooseDeleteFlag, setChooseDeleteFlag] = useState(false);

  function createBook() {
    axios({
      'method': 'POST',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: 1,
        name: name,
        publishDate: publishDate,
        authorId: getAuthorIdFromName(authorName),
      }
    }).then(response => {
      if (response.status === 201) {
        getAllBooks();
        setCreateFlag(false);
        clearBookFields();
      }
    }).catch(error => {
      alert('can\'t create book \n' + error);
      setCreateFlag(false);
      clearBookFields();
    });
  }

  function getAllBooks(listNameFlag = false) {
    axios({
      'method': 'GET',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setBooks(response.data)
        if (!listNameFlag) {
          setData(formBooksHtml(response.data));
        }
      }
    }).catch(error => {
      alert('can\'t read all books \n' + error);
    });
  }

  function getBook() {
    axios({
      'method': 'GET',
      'url': url + "/" + searchText,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setSearchText('');
        setData(formBookHtml(response.data));
      } else {
        alert("There is no Book with id: " + searchText);
        setSearchText('');
      }
    }).catch(error => {
      alert('can\'t read book \n' + error);
      setSearchText('');
    });
  }

  function updateBook() {
    axios({
      'method': 'PUT',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: bookId,
        name: name,
        publishDate: publishDate,
        authorId: getAuthorIdFromName(authorName),
      }
    }).then(response => {
      if (response.status === 200) {
        setUpdateFlag(false);
        setChooseUpdateFlag(false);
        setChooseModalBodyFlag(false);
        clearBookFields();
        getAllBooks();
      }
    }).catch(error => {
      alert('can\'t update \n' + error);
      setUpdateFlag(false);
      setChooseUpdateFlag(false);
      setChooseModalBodyFlag(false);
      clearBookFields();
    });
  }

  function deleteBook() {
    axios({
      'method': 'DELETE',
      'url': url + "/" + bookId,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setDeleteFlag(false);
        setChooseDeleteFlag(false);
        setChooseModalBodyFlag(false);
        clearBookFields();
        getAllBooks();
      }
    }).catch(error => {
      alert('can\'t delete \n' + error);
      setDeleteFlag(false);
      setChooseDeleteFlag(false);
      setChooseModalBodyFlag(false);
      clearBookFields();
    });
  }

  function clearBookFields() {
    setBookId(0);
    setName('');
    setPublishDate('');
    setSearchText('');
    setAuthorName('');
  }

  function showModal() {
    setShowFlag(true);
  }

  function handleSubmit(e) {
    e.preventDefault();
    setShowFlag(false);
    getBook();
  }

  function getRandomInt(max) {
    let randomValue = Math.floor(Math.random() * Math.floor(max));
    if (randomValue === 0) {
      randomValue = 4;
    }
    return randomValue + 6;
  }

  function getAuthorIdFromName(name) {
    let id = -1;
    authors.map((author) => {
      if (Object.is(name, author.name)) {
        id = author.id;
      }
    });
    return id;
  }

  function formBookHtml(item) {
    if (!Object.is(item, null)) {
      let html =
        <Row className='mt-5'>
          <Col className='text-center' lg={12} md={12} xs={12}>
            <ImageHolder id={getRandomInt(4)} name={item.name} />
          </Col>
          <Col lg={12} md={12} xs={12}>
            <Row className=''>
              <Col className='text-right' md={5} xs={6}>
                <strong>Name:</strong>
              </Col>
              <Col md={7} xs={6}>
                {item.name}
              </Col>
            </Row>
            <Row>
              <Col className='text-right' md={5} xs={6}>
                <strong>Publish date:</strong>
              </Col>
              <Col md={7} xs={6} className=''>
                {item.publishDate.dayOfMonth + " / " + item.publishDate.monthValue + " / " + item.publishDate.year}
              </Col>
            </Row>
            <Row>
              <Col className='text-right' md={5} xs={6}>
                <strong>Author:</strong>
              </Col>
              <Col md={7} xs={6} className=''>
                {item.author.name}
              </Col>
            </Row>
            {
              authorityFlag ?
                <Row className='text-center'>
                  <Col md={{ offset: 3, span: 6 }} xs={12}>
                    <Accordion>
                      <Card>
                        <Accordion.Toggle as={Card.Header} eventKey="0" className="text-center">
                          Click me!
                  </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                          <Card.Body as={Row}>
                            <Col className='mt-2 mb-2' xs={12}>
                              <Button variant='info' onClick={() => handleOperation(item)} block>Update</Button>
                            </Col>
                            <Col xs={12}>
                              <Button variant='danger' onClick={() => handleOperation(item, false, true)} block>Delete</Button>
                            </Col>
                          </Card.Body>
                        </Accordion.Collapse>
                      </Card>
                    </Accordion>
                  </Col>
                </Row> : ''
            }

          </Col>
        </Row>;
      return html;
    }
  }

  function formBooksHtml(items) {
    let htmls = [];
    items.map((item, index) => {
      let html =
        <Col lg={4} md={4} xs={6} key={index}>
          {
            formBookHtml(item)
          }
        </Col>;
      htmls.push(html);
    });
    htmls = <Row>{htmls}</Row>;
    return htmls;
  }

  function handleOperation(item, createflag = false, deleteFlag = false) {
    if (createflag) {
      setCreateFlag(true);
      return;
    }
    setBookId(item.id);
    setName(item.name);
    let date = new Date(item.publishDate.year, item.publishDate.monthValue - 1, item.publishDate.dayOfMonth);
    setPublishDate(date);
    setAuthorName(item.author.name);
    if (deleteFlag) {
      setDeleteFlag(true)
    } else {
      setUpdateFlag(true);
    }
  }

  function handleClose() {
    clearBookFields();
    setChooseModalBodyFlag(false);
    if (chooseUpdateFlag || chooseDeleteFlag) {
      setChooseUpdateFlag(false);
      setChooseDeleteFlag(false);
      return;
    }
    createFlag ? setCreateFlag(false) : updateFlag ? setUpdateFlag(false) : setDeleteFlag(false);
  }

  function getBookByName(name) {
    let bookToSearch = {};
    books.map((book) => {
      if (Object.is(name, book.name)) {
        bookToSearch = book;
      }
    });
    return bookToSearch;
  }

  function formModalBody() {
    return <Modal.Body>
      <Form>
        <Form.Group as={Row} >
          <Form.Label className='ml-2' column sm="3">Name:</Form.Label>
          <Col sm="8">
            <Form.Control type="text" className='w-75' style={{ alignCenter: 'center' }} placeholder='Type name' value={name} onChange={(e) => setName(e.target.value)} disableParameter />
          </Col>
        </Form.Group>
        <br />
        <Form.Group as={Row} >
          <Form.Label className='ml-2' column sm="3">PublishDate:</Form.Label>
          <Col sm="8">
            <Form.Control type="date" className='w-75' style={{ alignCenter: 'center' }} value={publishDate} onChange={(e) => setPublishDate(e.target.value)} disableParameter />
          </Col>
        </Form.Group>
        <br />
        <Form.Group as={Row}>
          <Form.Label className='ml-2' column sm="3">Author:</Form.Label>
          <Col sm="8">
            <Form.Control as="select" className='w-75' defaultValue={
              (createFlag) ? "Choose" : authorName
            } value={authorName} onChange={(e) => setAuthorName(e.target.value)}>
              <option>Choose</option>
              {
                authors.map((author, index) =>
                  <option key={index}>
                    {author.name}
                  </option>
                )
              }
            </Form.Control>
          </Col>
        </Form.Group>
        <br />
      </Form>
    </Modal.Body>
  }

  function handleChooseOperation(name) {
    setSearchText(name);
    if (Object.is('choose', name)) {
      setChooseModalBodyFlag(false);
      return;
    }
    let item = getBookByName(name);
    setBookId(item.id);
    setName(item.name);
    let date = new Date(item.publishDate.year, item.publishDate.monthValue - 1, item.publishDate.dayOfMonth);
    setPublishDate(date);
    setAuthorName(item.author.name);
    setChooseModalBodyFlag(true);
  }
  useEffect(() => {
    if (flag) {
      getAllBooks(true);
      setFlag(false);
    }
  });
  return (
    <>
      <Col md={3} xs={12}>
        <Dropdown as={ButtonGroup}>
          <Dropdown.Toggle className="width-100">
            <p className='nav-menu'>Books</p>
          </Dropdown.Toggle>
          <Dropdown.Menu className="width-100">
            {
              authorityFlag ? <Dropdown.Item eventKey="1" className='pl-5 h4' onClick={() => handleOperation('', true)}>Add new book</Dropdown.Item> : ''
            }
            <Dropdown.Item eventKey="2" className='pl-5 h4' onClick={() => showModal()}>Find book</Dropdown.Item>
            <Dropdown.Item eventKey="3" className='pl-5 h4' onClick={() => getAllBooks()}>Find books</Dropdown.Item>
            {
              authorityFlag ?
                <>
                  <Dropdown.Item eventKey="4" className='pl-5 h4' onClick={() => setChooseUpdateFlag(true)}>Update book</Dropdown.Item>
                  <Dropdown.Divider />
                  <Dropdown.Item eventKey="5" className='pl-5 h4' onClick={() => setChooseDeleteFlag(true)}>Delete book</Dropdown.Item>
                </> : ''
            }
          </Dropdown.Menu>
        </Dropdown>
      </Col>
      <Modal show={showFlag} onHide={() => setShowFlag(false)}>
        <Modal.Header closeButton>
          <Modal.Title><p className='h3 ml-5'>Find book</p></Modal.Title>
        </Modal.Header>
        <Modal.Body >
          <Form onSubmit={(e) => handleSubmit(e)}>
            <Form.Group>
              <Form.Control type="text" className='ml-5 w-75' style={{ alignCenter: 'center' }} placeholder="Type name or author of book" value={searchText} onChange={(e) => setSearchText(e.target.value)} />
              <Form.Text className="ml-5 text-muted">
                Press enter when you have already typed
              </Form.Text>
            </Form.Group>
          </Form>
        </Modal.Body>
      </Modal>
      <Modal show={updateFlag || createFlag || deleteFlag} onHide={() => handleClose()}>
        <Modal.Header closeButton>
          <Modal.Title><p className='h3 ml-2'>{createFlag ? "Create " : updateFlag ? "Update " : "Delete "} book</p></Modal.Title>
        </Modal.Header>
        {formModalBody()}
        <Modal.Footer>
          <Button variant={deleteFlag ? 'danger' : 'info'} type="submit" onClick={() => createFlag ? createBook() : updateFlag ? updateBook() : deleteBook()} block>
            {createFlag ? "Create" : updateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={chooseUpdateFlag || chooseDeleteFlag} onHide={() => handleClose()}>
        <Modal.Header closeButton>
          <p className='h3'>Book to {chooseUpdateFlag ? "update" : "delete"}</p>
          <Col lg={6} md={6} xs={6}>
            <Form.Control as="select" defaultValue={
              (createFlag) ? "choose" : searchText
            } onChange={(e) => handleChooseOperation(e.target.value)}>
              <option>choose</option>
              {
                books.map((book, index) =>
                  <option key={index}>
                    {book.name}
                  </option>
                )
              }
            </Form.Control>
          </Col>
        </Modal.Header>
        {
          chooseModalBodyFlag ? formModalBody() : ''
        }
        <Modal.Footer>
          <Button variant={chooseUpdateFlag ? 'info' : 'danger'} type="submit" onClick={() => chooseUpdateFlag ? updateBook() : deleteBook()} block>
            {chooseUpdateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Book;
