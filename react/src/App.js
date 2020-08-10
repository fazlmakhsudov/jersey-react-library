import React, { useState, useEffect} from 'react';
import Header from './component/header.js';
import Author from './component/author.js';
import Book from './component/book.js';
import { Container, Row, Col, Button } from 'react-bootstrap';
import './App.css';

function App() {
  let url = "http://localhost:8080/jersey-library";
  const [data, setData] = useState('');
  const [flag, setFlag] = useState(true);
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);

 useEffect(() => {
   console.log('useeff app: ', books, ' authors: ', authors );
 });

  return (
    <Container fluid='sm'>
      <Header />
      <Row className='mt-2'>
        <Col md={4} xs={12} className="" >
          <Button variant='info' className='' block><p className='nav-menu'>LIBRARY</p></Button>
        </Col>
        <Author url= {url} setData = {setData} setAuthors = {setAuthors} flag = {flag} setFlag = {setFlag} books = {books} />
        <Book url= {url} setData = {setData} setBooks = {setBooks} flag = {flag} setFlag = {setFlag} authors = {authors} />
      </Row>

      {
        data
      }

    </Container>
  );
}

export default App;
