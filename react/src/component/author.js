import React, { useState, useEffect } from 'react';
import ImageHolder from './image-holder.js';
import axios from 'axios';
import {
  Row, Col, ButtonGroup, Dropdown, Modal, Form, Button, Accordion, Card,
  OverlayTrigger, Popover, Tooltip
} from 'react-bootstrap';


function Author(props) {
  let url = props.url + "/author";
  let setData = props.setData;
  let setAuthors = props.setAuthors;
  let authorityFlag = props.authorityFlag;
  let authors = props.authors;
  let books = props.books;

  const [flag, setFlag] = useState(true);
  const [authorId, setAuthorId] = useState(0);
  const [showFlag, setShowFlag] = useState(false);
  const [modalType, setModalType] = useState('');
  const [searchText, setSearchText] = useState('');
  const [name, setName] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [updateFlag, setUpdateFlag] = useState(false);
  const [createFlag, setCreateFlag] = useState(false);
  const [deleteFlag, setDeleteFlag] = useState(false);
  const [chooseUpdateFlag, setChooseUpdateFlag] = useState(false);
  const [chooseDeleteFlag, setChooseDeleteFlag] = useState(false);
  const [chooseModalBodyFlag, setChooseModalBodyFlag] = useState(false);

  function createAuthor() {
    axios({
      'method': 'POST',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
      data: {
        id: 1,
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
      alert('can\'t create author \n' + error);
      setCreateFlag(false);
      clearAuthorFields();
    });
  }

  function getAllAuthors(listNameFlag = false) {
    axios({
      'method': 'GET',
      'url': url,
      'headers': {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.status === 200) {
        setAuthors(response.data)
        if (!listNameFlag) {
          setData(formAuthorsHtml(response.data));
        }
      }
    }).catch(error => {
      alert('can\'t read all authors \n' + error);
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
        setData(formAuthorHtml(response.data));
      } else {
        alert("There is no author: " + searchText);
      }
      setSearchText('');
    }).catch(error => {
      alert('no such author');
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
        setChooseUpdateFlag(false);
        setChooseModalBodyFlag(false);
        clearAuthorFields();
        getAllAuthors();
      }
    }).catch(error => {
      alert('can\'t update \n' + error);
      setUpdateFlag(false);
      setChooseUpdateFlag(false);
      setChooseModalBodyFlag(false);
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
        setChooseDeleteFlag(false);
        setChooseModalBodyFlag(false);
        clearAuthorFields();
        getAllAuthors();
      }
    }).catch(error => {
      alert('can\'t delete \n' + error);
      setDeleteFlag(false);
      setChooseDeleteFlag(false);
      setChooseModalBodyFlag(false);
      clearAuthorFields();
    });
  }

  function clearAuthorFields() {
    setAuthorId(0);
    setName('');
    setBirthdate('');
    setSearchText('');
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

  function getRandomInt(max, bookFlag = false) {
    let randomValue = Math.floor(Math.random() * Math.floor(max));
    if (randomValue === 0 && !bookFlag) {
      randomValue = 6;
    } else if (bookFlag) {
      randomValue = randomValue === 0 ? 10 : randomValue + 6;
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
            {
              authorityFlag ?
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
                            <Button variant='danger' onClick={() => handleOperation(item, false, true)} block>Delete</Button>
                          </Card.Body>
                        </Accordion.Collapse>
                      </Card>
                    </Accordion>
                  </Col>
                </Row> : ''
            }
            <Row className='mt-3 font-24'>
              <Col md={4} xs={6}>
                <strong>Books:</strong>
              </Col>
              {
                formBooksOfAuthor(item.id).map((book, index) => {
                  return <Col className='text-center' lg={4} md={4} xs={6} key={index}>
                    {
                      getPopOver(book)
                    }
                  </Col>
                })
              }
            </Row>
          </Col>
        </Row>;
      return html;
    }
  }

  function getPopOver(book) {
    return <OverlayTrigger placement='right' overlay={
      <Tooltip>
        <ImageHolder id={getRandomInt(4, true)} name={book.name} />
      </Tooltip>}>
      <p>{book.name}</p>
    </OverlayTrigger>
  }

  function formBooksOfAuthor(authorId) {
    let authorBook = [];
    books.map(book => {
      if (book.authorId === authorId) {
        authorBook.push(book);
      }
    });
    return authorBook;
  }

  function formAuthorsHtml(items) {
    let htmls = [];
    htmls.push(items.map((item, index) => {
      return <Col lg={6} md={6} xs={12} key={index}>
        {
          formAuthorHtml(item)
        }
      </Col>;
    }));

    htmls = <Row>{htmls}</Row>;
    return htmls;
  }

  function handleOperation(item, createflag = false, deleteFlag = false) {
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
      setDeleteFlag(true)
    } else {
      setUpdateFlag(true);
    }
  }

  function handleClose() {
    clearAuthorFields();
    setChooseModalBodyFlag(false);
    if (chooseUpdateFlag || chooseDeleteFlag) {
      setChooseUpdateFlag(false);
      setChooseDeleteFlag(false);
      return;
    }
    createFlag ? setCreateFlag(false) : updateFlag ? setUpdateFlag(false) : setDeleteFlag(false);
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
          <Form.Label className='ml-2' column sm="3">Birthdate:</Form.Label>
          <Col sm="8">
            <Form.Control type="date" className='w-75' style={{ alignCenter: 'center' }} value={birthdate} onChange={(e) => setBirthdate(e.target.value)} disableParameter />
          </Col>
        </Form.Group>
        <br />
      </Form>
    </Modal.Body>
  }

  function getAuthorByName(name) {
    let authorToSearch = {};
    authors.map((author) => {
      if (Object.is(name, author.name)) {
        authorToSearch = author;
      }
    });
    return authorToSearch;
  }

  function handleChooseOperation(name) {
    setSearchText(name);
    if (Object.is('choose', name)) {
      setChooseModalBodyFlag(false);
      return;
    }
    let item = getAuthorByName(name);
    setAuthorId(item.id);
    setName(item.name);
    let date = new Date(item.birthdate.year, item.birthdate.monthValue - 1, item.birthdate.dayOfMonth);
    setBirthdate(date);
    setChooseModalBodyFlag(true);
  }

  useEffect(() => {
    if (flag) {
      getAllAuthors(true);
      setFlag(false);
    }
  });

  return (
    <>
      <Col md={3} xs={12}>
        <Dropdown as={ButtonGroup}>
          <Dropdown.Toggle className="width-100">
            <p className='nav-menu'>Authors</p>
          </Dropdown.Toggle>
          <Dropdown.Menu className="width-100">
            {
              authorityFlag ?
                <Dropdown.Item eventKey="1" className='pl-5 h4' onClick={() => handleOperation('', true)}>Add new author</Dropdown.Item> : ''
            }
            <Dropdown.Item eventKey="2" className='pl-5 h4' onClick={() => showModal('author')}>Find author</Dropdown.Item>
            <Dropdown.Item eventKey="3" className='pl-5 h4' onClick={() => getAllAuthors()}>Find authors</Dropdown.Item>
            {
              authorityFlag ?
                <>
                  <Dropdown.Item eventKey="4" className='pl-5 h4' onClick={() => setChooseUpdateFlag(true)}>Update</Dropdown.Item>
                  <Dropdown.Divider />
                  <Dropdown.Item eventKey="4" className='pl-5 h4' onClick={() => setChooseDeleteFlag(true)}>Delete</Dropdown.Item>
                </> : ''
            }
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
        {formModalBody()}
        <Modal.Footer>
          <Button variant={deleteFlag ? 'danger' : 'info'} type="submit" onClick={() => createFlag ? createAuthor() : updateFlag ? updateAuthor() : deleteAuthor()} block>
            {createFlag ? "Create" : updateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={chooseUpdateFlag || chooseDeleteFlag} onHide={() => handleClose()}>
        <Modal.Header closeButton>
          <p className='h3'>Author to {chooseUpdateFlag ? "update" : "delete"}</p>
          <Col lg={6} md={6} xs={6}>
            <Form.Control as="select" defaultValue={
              (createFlag) ? "choose" : searchText
            } onChange={(e) => handleChooseOperation(e.target.value)}>
              <option>choose</option>
              {
                authors.map((author, index) =>
                  <option key={index}>
                    {author.name}
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
          <Button variant={chooseUpdateFlag ? 'info' : 'danger'} type="submit" onClick={() => chooseUpdateFlag ? updateAuthor() : deleteAuthor()} block>
            {chooseUpdateFlag ? "Update" : "Delete"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Author;
