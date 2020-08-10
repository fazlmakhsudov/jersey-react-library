import React from 'react';
import author1 from './resource/author1.jpeg';
import author2 from './resource/author2.jpeg';
import author3 from './resource/author3.jpeg';
import author4 from './resource/author4.jpeg';
import author5 from './resource/author5.jpeg';
import author6 from './resource/author6.jpeg';
import book1 from './resource/bookih1.jpg';
import book2 from './resource/bookih2.jpeg';
import book3 from './resource/bookih3.jpg';
import book4 from './resource/bookih4.jpg';
import Figure from 'react-bootstrap/Figure';


export default function ImageHolder(props) {
    function returnImage() {
        let image = '';
        switch (props.id) {
            case 1:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={author1}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in March 1961
                    </Figure.Caption>
                </Figure>;
                break;
            case 2:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={author2}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in August 1964
                </Figure.Caption>
                </Figure>;
                break;
            case 3:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={author3}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in August 1987
                </Figure.Caption>
                </Figure>;
                break;
            case 4:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={author4}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in December 1989
                </Figure.Caption>
                </Figure>;
                break;
            case 5:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={author5}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in August 1993
                </Figure.Caption>
                </Figure>;
                break;
            case 6:
                image = <Figure>
                    <Figure.Image
                        // width={400}
                        height={400}
                        alt="171x180"
                        src={author6}
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in July 1995
                </Figure.Caption>
                </Figure>;
                break;
            case 7:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={book1}
                        rounded
                    />
                </Figure>;
                break;
            case 8:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={book2}
                        rounded
                    />
                </Figure>;
                break;
            case 9:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={book3}
                        rounded
                    />
                </Figure>;
                break;
            case 10:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="171x180"
                        src={book4}
                        rounded
                    />
                </Figure>;
                break;
            default:
                image = <Figure>
                    <Figure.Image
                        width={400}
                        height={400}
                        alt="Invisible man"
                        rounded
                    />
                    <Figure.Caption>
                        {props.name} in August 2020
                </Figure.Caption>
                </Figure>;

        }
        return image;
    }
    return (
        <div>
            {
                returnImage()
            }
        </div>
    );
}
