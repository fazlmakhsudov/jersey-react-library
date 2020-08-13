import React, { useState, useEffect } from 'react';
import Header from './component/header.js';
import Author from './component/author.js';
import Book from './component/book.js';
import ModalWindow from './component/modal.js';
import { Container, Row, Col, Button, Image } from 'react-bootstrap';
import BookAnime1 from './component/resource/books1.gif';
import './App.css';

function App() {
  let url = "http://localhost:8080/jersey-library";
  const [data, setData] = useState('');
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [authorityFlag, setAuthorityFlag] = useState(false);
  const [showFlag, setShowFlag] = useState(true);
  const [mainPageFlag, setMainPageFlag] = useState(true);

  function recommend() {
    if (authors.length === 0 || books.length === 0) {
      return '';
    }
    let html = [];
    html.push(
      <div>
        <h2>Our top authors: </h2>

      </div>
    );
    for (const [index, value] of authors.entries()) {
      html.push(<strong key={index} />)
    }
  }

  function showMainPage() {
    let html = <Row className='mt-5 text-center' >
      <Col md={{offset:3, span:6}} xs={12}>
        <Image className="mt-5" src={BookAnime1} width={400} height={400} rounded />
      </Col>
    </Row>;
    setData(html);
  }

  useEffect(() => {
    if (mainPageFlag) {
      showMainPage();
      setMainPageFlag(false);
    }

  });

  return (
    <Container fluid='sm'>
      <Header />
      <Row className='mt-2'>
        <Col md={4} xs={12} className="" >
          <Button variant='info' className='' onClick={() => setMainPageFlag(true)} block><p className='nav-menu'>LIBRARY</p></Button>
        </Col>
        <Author url={url} setData={setData} setAuthors={setAuthors} authorityFlag={authorityFlag} books={books} authors={authors} />
        <Book url={url} setData={setData} setBooks={setBooks} authorityFlag={authorityFlag} authors={authors} books={books} />
      </Row>
      {
        data
      }
      <ModalWindow showFlag={showFlag} setShowFlag={setShowFlag} url={url} setAuthorityFlag={setAuthorityFlag} />
    </Container>
  );
}

export default App;
