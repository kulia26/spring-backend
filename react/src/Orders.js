import React from 'react';
import Card from './Card';
import axios from 'axios';
import categories from './categories';
import OrderItem from './OrderItem';
import { Redirect } from "react-router-dom";

class Orders extends React.Component {
  constructor(props) {
    super(props);
    let user = sessionStorage.getItem('user');
    user = user ? JSON.parse(user) : {} ;
    this.state = {
      category: props.category, 
      isAdmin: props.isAdmin,
      title: props.title,
      user: user,
      items: [],
    };
    this.componentDidMount = this.componentDidMount.bind(this);
    this.renderBasketItems = this.renderBasketItems.bind(this);
    this.renderOrderItems = this.renderOrderItems.bind(this);
    this.someItemAreRemoved = this.someItemAreRemoved.bind(this);
  }

componentDidMount() {
  if(this.state.user && this.state.user.phone){
     console.log(this.state.user);
  const categories = [
    'laptops',
    'tablets',
    'smartphones',
    'keyboards',
    'mouses',
    'headphones',
    'players'
  ];
  let token = JSON.parse(sessionStorage.getItem('token'));
  let config = {};
  if(token){
    config = {
      headers: {
          "Authorization" : token.tokenType+' '+ token.accessToken,
      }
    }
  }
  
  if(this.props.isAdmin){
    console.log('admin get all orderItems');
    axios.get('http://localhost:8000/orderItems', config)
    .then(res => {
      this.setState({items: res.data});
    })
    .catch(err=>{
      window.alert(err);
    })
  }else{
    console.log('user get his orderItems');
      axios.get('http://localhost:8000/users/'+this.state.user.phone+'/', config)
      .then(res => {
        this.setState({items: res.data.orderItems});
      })
      .catch(err=>{
        window.alert(err);
      })
  }
  }
  
}

someItemAreRemoved(item){
  this.componentDidMount();
  // !!! Сделать здесь запрос на сервер чтобы обновить козину и заказы
}

renderBasketItems(){
  let render = [];
  console.log(this.state.items);
  if(this.state.items.length > 0){
    for (const key in this.state.items) {
      const el = this.state.items[key];
      if(!el.isCompleted && el && el.product){
        render.push(<OrderItem 
          isAdmin={this.props.isAdmin} 
          onEdit={this.onEdit} 
          onDelete={this.someItemAreRemoved} 
          item={el} 
          key={el.id}
          title={el.product.name}
          time={el.createdAt}
          description={el.product.description} 
          cost={el.product.cost}></OrderItem>)  
      }    
    }
  }
  return render;
}

renderOrderItems(){
  let render = [];
    for (const key in this.state.items) {
      const el = this.state.items[key];
      if(el.isCompleted && el){
        render.push(<OrderItem 
          isAdmin={this.props.isAdmin} 
          onEdit={this.onEdit} 
          onDelete={this.someItemAreRemoved} 
          item={el} 
          key={el.id}
          title={el.product.name}
          description={el.product.description} 
          time={el.createdAt}
          cost={el.product.cost}></OrderItem>)  
      }    
    }
  return render;
}

render()
  {return (
    <>
    {!this.state.user ? <Redirect to="/login" /> : <></>}
    <div className="flex">
      <h1>Корзина</h1>
    </div>
    <section className="flex">
    {this.renderBasketItems()}
    </section>
    <div className="flex">
      <h1>Минулі замовлення</h1>
    </div>
    <section className="flex">
    {this.renderOrderItems()}
    </section>
    </>
  );}
}

export default Orders;
