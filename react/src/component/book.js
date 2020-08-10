import React, { useState, useRef } from 'react';
import ImageHolder from './image-holder.js';
import axios from 'axios';
import { Row, Col, ButtonGroup, Dropdown, Modal, Form, Button, Accordion, Card } from 'react-bootstrap';


function Book(props) {
  let url = props.url + "/book";
  let setData = props.setData;

  const [bookId, setBookId] = useState(null);
  const [books, setBooks] = useState([]);
  const [showFlag, setShowFlag] = useState(false);
  const [modalType, setModalType] = useState('');
  const [searchText, setSearchText] = useState('');
  const [name, setName] = useState('');
  const [publishDate, setPublishDate] = useState('');
  const [updateFlag, setUpdateFlag] = useState(false);
  const [createFlag, setCreateFlag] = useState(false);
  const [deleteFlag, setDeleteFlag] = useState(false);
  const [disableParameter, setDisableParameter] = useState("disabled");

  function createBook() {
    axios({
      'method': 'POST',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: '1',
        name: name,
        publishDate: publishDate,
      }
    }).then(response => {
      if (response.status === 201) {
        getAllBooks();
        setCreateFlag(false);
        clearBookFields();
      }
    }).catch(error => {
      setCreateFlag(false);
      clearBookFields();
    });
  }

  function getAllBooks() {
    axios({
      'method': 'GET',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setBooks(response.data);
        setData(formBooksHtml(response.data));
      }
    }).catch(error => {
      console.log('erroring from getAllAthors: ', error);
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
      console.log('erroring from getAthor: ', error);
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
      }
    }).then(response => {
      if (response.status === 200) {
        setUpdateFlag(false);
        clearBookFields();
        getAllBooks();
      }
    }).catch(error => {
      setUpdateFlag(false);
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
        clearBookFields();
        getAllBooks();
      }
    }).catch(error => {
      setDeleteFlag(false);
      clearBookFields();
    });
  }

  function clearBookFields() {
    setBookId('');
    setName('');
    setPublishDate('');
    setDisableParameter('');
  }

  function showModal(type) {
    setShowFlag(true);
    setModalType(type);
  }

  function handleSubmit(e) {
    e.preventDefault();
    setShowFlag(false);
    if (modalType === 'book') {
      getBook();
    }
  }

  function getRandomInt(max) {
    let randomValue = Math.floor(Math.random() * Math.floor(max));
    if (randomValue === 0) {
      randomValue = 4;
    }
    return randomValue + 6;
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
              <Col  className='text-right' md={5} xs={6}>
                <strong>Name:</strong>
              </Col>
              <Col md={7} xs={6}>
                {item.name}
              </Col>
            </Row>
            <Row>
              <Col  className='text-right' md={5} xs={6}>
                <strong>Publish date:</strong>
              </Col>
              <Col md={7} xs={6} className=''>
                {item.publishDate.dayOfMonth + " / " + item.publishDate.monthValue + " / " + item.publishDate.year}
              </Col>
            </Row>
            <Row className=''>
              <Col>
                <Accordion>
                  <Card>
                    <Accordion.Toggle as={Card.Header} eventKey="0" className="text-center">
                      Click me!
                  </Accordion.Toggle>
                    <Accordion.Collapse eventKey="0">
                      <Card.Body as={Row}>
                        <Col md={6} xs={12}>
                          <Button variant='info' onClick={() => handleOperation(item)} block>Update</Button>
                        </Col>
                        <Col md={6} xs={12}>
                          <Button variant='danger' onClick={() => handleOperation(item, false, true)} block>Delete</Button>
                        </Col>
                      </Card.Body>
                    </Accordion.Collapse>
                  </Card>
                </Accordion>
              </Col>
            </Row>
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
    setModalType('book');
    if (createflag) {
      setCreateFlag(true);
      return;
    }
    setBookId(item.id);
    setName(item.name);
    let date = new Date(item.publishDate.year, item.publishDate.monthValue - 1, item.publishDate.dayOfMonth);
    setPublishDate(date);
    if (deleteFlag) {
      setDisableParameter('disabled');
      setDeleteFlag(true)
    } else {
      setUpdateFlag(true);
    }
  }

  function handleClose() {
    createFlag ? setCreateFlag(false) : updateFlag ? setUpdateFlag(false) : setDeleteFlag(false);
    clearBookFields();
  }

  return (
    <>
      <Col md={3} xs={12}>
        <Dropdown as={ButtonGroup}>
          <Dropdown.Toggle className="width-100">
            <p className='nav-menu'>Books</p>
          </Dropdown.Toggle>
          <Dropdown.Menu className="width-100">
            <Dropdown.Item eventKey="1" className='pl-5 h4' onClick={() => handleOperation('', true)}>Add new book</Dropdown.Item>
            <Dropdown.Item eventKey="2" className='pl-5 h4' onClick={() => showModal('book')}>Find book</Dropdown.Item>
            <Dropdown.Item eventKey="3" className='pl-5 h4' onClick={() => getAllBooks()}>Find books</Dropdown.Item>
            <Dropdown.Item eventKey="4" className='pl-5 h4'>Update book</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item eventKey="5" className='pl-5 h4'>Delete book</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Col>
      <Modal show={showFlag} onHide={() => setShowFlag(false)}>
        <Modal.Header closeButton>
          <Modal.Title><p className='h3 ml-5'>Find {modalType}</p></Modal.Title>
        </Modal.Header>
        <Modal.Body >
          <Form onSubmit={(e) => handleSubmit(e)}>
            <Form.Group>
              <Form.Control type="text" className='ml-5 w-75' style={{ alignCenter: 'center' }} placeholder={"Type name of " + modalType} value={searchText} onChange={(e) => setSearchText(e.target.value)} />
              <Form.Text className="ml-5 text-muted">
                Press enter when you have already typed
              </Form.Text>
            </Form.Group>
          </Form>
        </Modal.Body>
      </Modal>
      <Modal show={updateFlag || createFlag || deleteFlag} onHide={() => handleClose()}>
        <Modal.Header closeButton>
          <Modal.Title><p className='h3 ml-2'>{createFlag ? "Create " : updateFlag ? "Update " : "Delete "} {modalType}</p></Modal.Title>
        </Modal.Header>
        <Modal.Body >
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
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant={deleteFlag ? 'danger' : 'info'} type="submit" onClick={() => createFlag ? createBook() : updateFlag ? updateBook() : deleteBook()} block>
            {createFlag ? "Create" : updateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Book;
