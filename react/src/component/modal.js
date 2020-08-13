import React, { useState } from 'react';
import { Form, Modal, Row, Col, Button } from 'react-bootstrap';
import axios from 'axios';

function ModalWindow(props) {
    let url = props.url + "/user";
    let showFlag = props.showFlag;
    let setShowFlag = props.setShowFlag;
    let setAuthorityFlag = props.setAuthorityFlag;

    const [name, setName] = useState('');
    const [password, setPassword] = useState('');

    function checkCredential() {
        console.log('check credentioal:', name, password);
        axios({
            'method': 'POST',
            'url': url + "/signin",
            'headers': {
                'Content-Type': 'application/json',
            },
            data: {
                id : 1,
                name: name,
                password: password,
            }
        }).then(response => {
            console.log('respone user: ', response);
            if (response.status === 200) {
                setAuthorityFlag(true);
            }
        }).catch(error => {
            alert('It has appeared \n' + error);
        });
    }

    function handleSubmit() {
        setShowFlag(false);
        checkCredential();
    }

    return (
        <Modal show={showFlag} onHide={() => setShowFlag(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Sign in</Modal.Title>
            </Modal.Header>
            <Modal.Body as={Row} className='text-center'>
                <Form as={Col} className='w-50'>
                    <Form.Group>
                        <Form.Control type="text" value={name} placeholder="Type login" onChange={(e) => setName(e.target.value)} />
                    </Form.Group>
                </Form>
                <Form as={Col} className='w-50'>
                    <Form.Group>
                        <Form.Control type="password" value={password} placeholder="Type password" onChange={(e) => setPassword(e.target.value)} />
                    </Form.Group>
                </Form>
                <Col sm={12}>
                    <Button type='submit' onClick={() => handleSubmit()} block>Click me</Button>
                </Col>
            </Modal.Body>
        </Modal>
    );
}

export default ModalWindow;