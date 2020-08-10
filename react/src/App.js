import React, { useState} from 'react';
import Header from './component/header.js';
import Author from './component/author.js';
import Book from './component/book.js';
import { Container, Row, Col, Button } from 'react-bootstrap';
import './App.css';

function App() {
  let url = "http://localhost:8080/jersey-library";
  const [data, setData] = useState('');
 

  return (
    <Container fluid='sm'>
      <Header />
      <Row className='mt-2'>
        <Col md={4} xs={12} className="" >
          <Button variant='info' className='' block><p className='nav-menu'>LIBRARY</p></Button>
        </Col>
        <Author url= {url} setData = {setData}/>
        <Book url= {url} setData = {setData}/>
      </Row>

      {
        data
      }

    </Container>
  );
}

export default App;
