import React, { useState } from 'react';
import { Form } from 'react-bootstrap';

function Modal(props) {
    const [showFlag, setShowFlag] = useState(props.showFlag);
    return (
        <Modal show={showFlag} onHide={() => setShowFlag(false)}>
            {
                console.log(showFlag + "   " + props.showFlag)
            }
            <Modal.Header closeButton>
                <Modal.Title>{props.title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Control type="text" placeholder="Type name {props.title}"  />
                        <Form.Text className="text-muted">
                            Press enter when you have already typed 
                        </Form.Text>
                    </Form.Group>
                </Form>
            </Modal.Body>
        </Modal>
    );
}

export default Modal;