import React from 'react';

const ItemRow = ({sku, name, stock, capacity, orderChangeCallBack}) => {

    const handleChange = e => {
        orderChangeCallBack(e)
    }

    return (
        <tr>
            <td>{sku}</td>
            <td>{name}</td>
            <td>{stock}</td>
            <td>{capacity}</td>
            <td><input
                name={name}
                type="number"
                min="0"
                max={capacity-stock}
                onChange={handleChange}
            /></td>
        </tr>
    );
}

export default ItemRow;
