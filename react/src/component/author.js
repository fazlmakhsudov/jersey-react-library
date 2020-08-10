import React, { useState, useRef } from 'react';
import ImageHolder from './image-holder.js';
import axios from 'axios';
import { Row, Col, ButtonGroup, Dropdown, Modal, Form, Button, Accordion, Card } from 'react-bootstrap';


function Author(props) {
  let url = props.url + "/author";
  let setData = props.setData;

  const [authorId, setAuthorId] = useState(null);
  const [authors, setAuthors] = useState([]);
  const [showFlag, setShowFlag] = useState(false);
  const [modalType, setModalType] = useState('');
  const [searchText, setSearchText] = useState('');
  const [name, setName] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [updateFlag, setUpdateFlag] = useState(false);
  const [createFlag, setCreateFlag] = useState(false);
  const [deleteFlag, setDeleteFlag] = useState(false);
  const [disableParameter, setDisableParameter] = useState("disabled");

  function createAuthor() {
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
      if (response.status === 201) {
        getAllAuthors();
        setCreateFlag(false);
        clearAuthorFields();
      }
    }).catch(error => {
      setCreateFlag(false);
      clearAuthorFields();
    });
  }

  function getAllAuthors() {
    axios({
      'method': 'GET',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setAuthors(response.data);
        setData(formAuthorsHtml(response.data));
      }
    }).catch(error => {
      console.log('erroring from getAllAthors: ', error);
    });
  }

  function getAuthor() {
    axios({
      'method': 'GET',
      'url': url + "/" + searchText,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setSearchText('');
        setData(formAuthorHtml(response.data));
      } else {
        alert("There is no author with id: " + searchText);
        setSearchText('');
      }
    }).catch(error => {
      console.log('erroring from getAthor: ', error);
      setSearchText('');
    });
  }

  function updateAuthor() {
    axios({
      'method': 'PUT',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: authorId,
        name: name,
        birthdate: birthdate,
      }
    }).then(response => {
      if (response.status === 200) {
        setUpdateFlag(false);
        clearAuthorFields();
        getAllAuthors();
      }
    }).catch(error => {
      setUpdateFlag(false);
      clearAuthorFields();
    });
  }

  function deleteAuthor() {
    axios({
      'method': 'DELETE',
      'url': url + "/" + authorId,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setDeleteFlag(false);
        clearAuthorFields();
        getAllAuthors();
      }
    }).catch(error => {
      setDeleteFlag(false);
      clearAuthorFields();
    });
  }

  function clearAuthorFields() {
    setAuthorId('');
    setName('');
    setBirthdate('');
    setDisableParameter('');
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
                        <Button variant='info' onClick={() => handleOperation(item)} block>Update</Button>
                        <Button variant='danger' onClick={() => handleOperation(item,false,true)} block>Delete</Button>
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
    let htmls = [];
    items.map((item, index) => {
      let html =
        <Col lg={6} md={6} xs={12} key={index}>
          {
            formAuthorHtml(item)
          }
        </Col>;
      htmls.push(html);
    });
    htmls = <Row>{htmls}</Row>;
    return htmls;
  }

  function handleOperation(item, createflag = false, deleteFlag=false) {
    setModalType('author');
    if (createflag) {
      setCreateFlag(true);
      return;
    } 
    setAuthorId(item.id);
    setName(item.name);
    let date = new Date(item.birthdate.year, item.birthdate.monthValue - 1, item.birthdate.dayOfMonth);
    setBirthdate(date);
    if (deleteFlag) {
      setDisableParameter('disabled');
      setDeleteFlag(true)
    } else {
      setUpdateFlag(true);
    }
  }

  function handleClose() {
    createFlag ? setCreateFlag(false) : updateFlag ? setUpdateFlag(false) : setDeleteFlag(false);
    clearAuthorFields();
  }

  return (
    <>
      <Col md={3} xs={12}>
        <Dropdown as={ButtonGroup}>
          <Dropdown.Toggle className="width-100">
            <p className='nav-menu'>Authors</p>
          </Dropdown.Toggle>
          <Dropdown.Menu className="width-100">
            <Dropdown.Item eventKey="1" className='pl-5 h4' onClick={() => handleOperation('', true)}>Add new author</Dropdown.Item>
            <Dropdown.Item eventKey="2" className='pl-5 h4' onClick={() => showModal('author')}>Find author</Dropdown.Item>
            <Dropdown.Item eventKey="3" className='pl-5 h4' onClick={() => getAllAuthors()}>Find authors</Dropdown.Item>
            <Dropdown.Item eventKey="4" className='pl-5 h4'>Update author</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item eventKey="5" className='pl-5 h4'>Delete author</Dropdown.Item>
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
                <Form.Control type="text" className='w-75' style={{ alignCenter: 'center' }} placeholder='Type name' value={name} onChange={(e) => setName(e.target.value)} disableParameter/>
              </Col>
            </Form.Group>
            <br />
            <Form.Group as={Row} >
              <Form.Label className='ml-2' column sm="3">Birthdate:</Form.Label>
              <Col sm="8">
                <Form.Control type="date" className='w-75' style={{ alignCenter: 'center' }} value={birthdate} onChange={(e) => setBirthdate(e.target.value)} disableParameter/>
              </Col>
            </Form.Group>
            <br />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant={deleteFlag ? 'danger' : 'info'} type="submit" onClick={() => createFlag ? createAuthor() : updateFlag ? updateAuthor() : deleteAuthor()} block>
            {createFlag ? "Create" : updateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Author;
