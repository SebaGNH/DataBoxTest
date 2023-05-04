import React from 'react';
import image from '../../../assets/img/empty_state_catedras.png';

interface TableMessageI {
    msg?: any;
    helperText?: any;
}

const style = {
    display: 'flex',
    flexDirection: 'column' as unknown as undefined,
    alignItems: 'center'
};

const imgStyle = {
    width: 200
};

const TableMessage = ({ msg = '', helperText = '' }: TableMessageI) => {
    return (
        <div className="table__message" style={style}>
            <img src={image} style={imgStyle}></img>
            <h5>{msg}</h5>
            <p>{helperText}</p>
        </div>
    );
};

export default TableMessage;
