import React, { useState, useRef } from 'react';
import logo from './logo.svg';
import './App.css';
import Header from './component/header';
import ImageHolder from './component/image-holder.js';
import axios from 'axios';
import { Container, Row, Col, ButtonGroup, Dropdown, Modal, Form, Button, Accordion, Card } from 'react-bootstrap';
import './App.css';

function App() {
  let url = "http://localhost:8080/jersey-library/author";
  const [book, setBook] = useState({});
  const [books, setBooks] = useState({});
  const [authorId, setAuthorId] = useState(null);
  const [authors, setAuthors] = useState([]);
  const [showFlag, setShowFlag] = useState(false);
  const [modalType, setModalType] = useState('');
  const [searchText, setSearchText] = useState('');
  const [data, setData] = useState('');
  const [name, setName] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [updateFlag, setUpdateFlag] = useState(false);
  const [createFlag, setCreateFlag] = useState(false);

  function createAuthor() {
    console.log('create');
    axios({
      'method': 'POST',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: '1',
        name: name,
        birthdate: birthdate,
      }
    }).then(response => {
      console.log('responsing from createAuthor: ', response);
      if (response.status === 201) {
        getAllAuthors();
        setCreateFlag(false);
        clearAuthorFields();
      }
    }).catch(error => {
      console.log('erroring from createAthors: ', error);
      setCreateFlag(false);
      clearAuthorFields();
    });
  }

  function getAllAuthors() {
    console.log('getAllAuthors');
    axios({
      'method': 'GET',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      console.log('responsing from getAllAuthors: ', response);
      if (response.status === 200) {
        setAuthors(response.data);
        setData(formAuthorsHtml(response.data));
      }
    }).catch(error => {
      console.log('erroring from getAllAthors: ', error);
    });
  }

  function getAuthor() {
    console.log('getAuthor');
    axios({
      'method': 'GET',
      'url': url + "/" + searchText,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      console.log('response', response)
      if (response.status === 200) {
        setSearchText('');
        // setAuthor(response.data);
        setData(formAuthorHtml(response.data));
      } else {
        // setAuthor(null);
        alert("There is no author with id: " + searchText);
        setSearchText('');
      }
    }).catch(error => {
      console.log('erroring from getAllAthors: ', error);
      setSearchText('');
    });
  }

  function updateAuthor() {
    console.log('update');
    axios({
      'method': 'PUT',
      'url': url,
      'headers': {
        // 'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      data: {
        id: authorId,
        name: name,
        birthdate: birthdate,
      }
    }).then(response => {
      console.log('responsing from updateAuthor: ', response);
      if (response.status === 200) {
        setUpdateFlag(false);
        clearAuthorFields();
        getAllAuthors();
      }
    }).catch(error => {
      console.log('erroring from getAllAthors: ', error);
      setUpdateFlag(false);
      clearAuthorFields();
    });
  }

  function clearAuthorFields() {
    setAuthorId('');
    setName('');
    setBirthdate('');
  }

  function showModal(type) {
    setShowFlag(true);
    setModalType(type);
  }

  function handleSubmit(e) {
    e.preventDefault();
    setShowFlag(false);
    if (modalType === 'author') {
      getAuthor();
    }
  }

  function getRandomInt(max) {
    let randomValue = Math.floor(Math.random() * Math.floor(max));
    if (randomValue === 0) {
      randomValue = 6;
    }
    return randomValue;
  }

  function formAuthorHtml(item) {
    console.log('author form, ' + item.name);
    let date = new Date();
    if (!Object.is(item, null)) {
      let html =
        <Row className='mt-5'>
          <Col className='text-center' lg={6} md={6} xs={12}>
            <ImageHolder id={getRandomInt(6)} name={item.name} />
          </Col>
          <Col lg={6} md={6} xs={12}>
            <Row className='font-24'>
              <Col md={3} xs={6}>
                <strong>Name:</strong>
              </Col>
              <Col md={9} xs={6}>
                {item.name}
              </Col>
            </Row>
            <Row className='font-24'>
              <Col md={4} xs={6}>
                <strong>Birthdate:</strong>
              </Col>
              <Col md={8} xs={6}>
                {item.birthdate.dayOfMonth + " / " + item.birthdate.monthValue + " / " + item.birthdate.year}
              </Col>
            </Row>
            <Row className='font-24'>
              <Col lg={12} md={12} xs={12}>
                <Accordion>
                  <Card>
                    <Accordion.Toggle as={Card.Header} eventKey="0" className="text-center">
                      Click me!
                  </Accordion.Toggle>
                    <Accordion.Collapse eventKey="0">
                      <Card.Body>
                        <Button variant='info' onClick={() => handleUpdateCreate(item)} block>Update</Button>
                        <Button variant='danger' block>Delete</Button>
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

  function formAuthorsHtml(items) {
    console.log('author form, ' + items.length);
    let htmls = [];
    items.map((item) => {
      let html =
        <Col lg={6} md={6} xs={12}>
          {
            formAuthorHtml(item)
          }
        </Col>;
      htmls.push(html);
    });
    console.log('htmls formAuthors: ', htmls);
    htmls = <Row>{htmls}</Row>;
    return htmls;
  }

  function handleUpdateCreate(item, flag = false) {
    setModalType('author');
    if (flag) {
      setCreateFlag(true);
      return;
    }
    setAuthorId(item.id);
    setName(item.name);
    let date = new Date(item.birthdate.year, item.birthdate.monthValue - 1, item.birthdate.dayOfMonth);
    setBirthdate(date);
    setUpdateFlag(true);
  }

  function handleClose() {
    createFlag ? setCreateFlag(false) : setUpdateFlag(false);
    clearAuthorFields();
  }

  return (
    <Container fluid='sm'>
      <Header />
      <Row className='mt-2'>
        <Col md={4} xs={12} className="" >
          <Button variant='info' className='' block><p className='nav-menu'>LIBRARY</p></Button>
        </Col>
        <Col md={3} xs={12}>
          <Dropdown as={ButtonGroup}>
            <Dropdown.Toggle className="width-100">
              <p className='nav-menu'>Authors</p>
            </Dropdown.Toggle>
            <Dropdown.Menu className="width-100">
              <Dropdown.Item eventKey="1" className='pl-5 h4' onClick={() => handleUpdateCreate({}, true)}>Add new author</Dropdown.Item>
              <Dropdown.Item eventKey="2" className='pl-5 h4' onClick={() => showModal('author')}>Find author</Dropdown.Item>
              <Dropdown.Item eventKey="3" className='pl-5 h4' onClick={() => getAllAuthors()}>Find authors</Dropdown.Item>
              <Dropdown.Item eventKey="4" className='pl-5 h4'>Update author</Dropdown.Item>
              <Dropdown.Divider />
              <Dropdown.Item eventKey="5" className='pl-5 h4'>Delete author</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Col>
        <Col md={3} xs={12}>
          <Dropdown as={ButtonGroup}>
            <Dropdown.Toggle className="width-100">
              <p className='nav-menu'>Books</p>
            </Dropdown.Toggle>
            <Dropdown.Menu className="width-100">
              <Dropdown.Item eventKey="1" className='pl-5 h4'>Add new book</Dropdown.Item>
              <Dropdown.Item eventKey="2" className='pl-5 h4'>Find book</Dropdown.Item>
              <Dropdown.Item eventKey="3" className='pl-5 h4'>Find books</Dropdown.Item>
              <Dropdown.Item eventKey="4" className='pl-5 h4'>Update book</Dropdown.Item>
              <Dropdown.Divider />
              <Dropdown.Item eventKey="5" className='pl-5 h4'>Delete book</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Col>
      </Row>

      {
        data
      }

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
      <Modal show={updateFlag || createFlag} onHide={() => handleClose()}>
        <Modal.Header closeButton>
          <Modal.Title><p className='h3 ml-2'>{createFlag ? "Create " : "Update "} {modalType}</p></Modal.Title>
        </Modal.Header>
        <Modal.Body >
          <Form>
            <Form.Group as={Row} >
              <Form.Label className='ml-2' column sm="2">Name:</Form.Label>
              <Col sm="9">
                <Form.Control type="text" className='w-75' style={{ alignCenter: 'center' }} placeholder='Type name' value={name} onChange={(e) => setName(e.target.value)} />
              </Col>
            </Form.Group>
            <br />
            <Form.Group as={Row} >
              <Form.Label className='ml-2' column sm="2">Birthdate:</Form.Label>
              <Col sm="9">
                <Form.Control type="date" className='w-75' style={{ alignCenter: 'center' }} value={birthdate} onChange={(e) => setBirthdate(e.target.value)} />
              </Col>
            </Form.Group>
            <br />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" type="submit" onClick={() => createFlag ? createAuthor() : updateAuthor()} block>
            {createFlag ? "Create" : "Update"}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default App;
