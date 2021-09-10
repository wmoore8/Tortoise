import { useState } from 'react';
import ItemRow from './ItemRow';

export default function Challenge() {

  //State
  const [items, setItems] = useState([]);
  const [orderCost, setOrderCost] = useState(0);

  const [orders, setOrders] = useState([]);


    //GET request
  const getLowStockItems = async () => {
      const response = await fetch('http://localhost:4567/low-stock',
          {headers: { 'Content-Type': 'application/json',
                           'Accept': 'application/json'}
          });
      const data = await response.json();
      setItems(data);
      setOrders(data);
  }

  //POST request
  const calculateRestockCost = async () => {
      const requestOptions = {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(orders)

      };
      const response = await fetch('http://localhost:4567/restock-cost', requestOptions);
      const data = await response.json();
      setOrderCost(data)
  }

  const handleOrderChangeCallBack = e => {
      let itemOrder = {
          name: e.target.name,
          restockAmount: e.target.value
      }

      setOrders(orders.map(item => {
          if(item.name === itemOrder.name) {
              if(itemOrder.restockAmount !== item.restockAmount) {
                  item.restockAmount = itemOrder.restockAmount
              }
          }
          return item
      }))
  }

  return (
    <>
      <table>
        <thead>
          <tr>
            <td>SKU</td>
            <td>Item Name</td>
            <td>Amount in Stock</td>
            <td>Capacity</td>
            <td>Order Amount</td>
          </tr>
        </thead>
        <tbody>
          {items.map(item => (
            <ItemRow
              sku={item.id}
              name={item.name}
              stock={item.stock}
              capacity={item.capacity}
              orderChangeCallBack={handleOrderChangeCallBack}
            />
          ))}
        {/*
        TODO: Create an <ItemRow /> component that's rendered for every inventory item. The component
        will need an input element in the Order Amount column that will take in the order amount and
        update the application state appropriately.
        */}

        </tbody>
      </table>
      {/* TODO: Display total cost returned from the server */}
      <div>Total Cost: {orderCost}</div>
      {/* 
      TODO: Add event handlers to these buttons that use the Java API to perform their relative actions.
      */}
      <button onClick={() => getLowStockItems()}>Get Low-Stock Items</button>
      <button onClick={() => calculateRestockCost()}>Determine Re-Order Cost</button>
    </>
  );
}
