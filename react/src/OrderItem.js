import React from 'react';
import { Link } from "react-router-dom";
const axios = require('axios');

class OrderItem extends React.Component {
    constructor(props) {
        super(props);
        let user = sessionStorage.getItem('user');
        user = user ? JSON.parse(user) : {};
        this.state = {
          item: this.props.el,
          user: user,
          phone: '', 
          name: ''
        };
        this.deleteThisItem = this.deleteThisItem.bind(this);
        this.addToBasket = this.addToBasket.bind(this);
        this.setCompleted = this.setCompleted.bind(this);
        this.getDate = this.getDate.bind(this);
        
        this.componentDidMount = this.componentDidMount.bind(this);
    }
    deleteThisItem() {
      const item = this.props.item;
      const url = 'http://localhost:8000/orderItems/'+item.id;
      let token = JSON.parse(sessionStorage.getItem('token'));
      if(token){
        const config = {
            headers: {
               "Authorization" : token.tokenType+' '+token.accessToken,
            },  
        }
        axios
        .delete(url, config)
        .then((res) => {
          this.setState({ message : res.data.message});
          this.props.onDelete(item);
        })
        .catch(err => this.setState({ message :  err.response.message}));
      }else{
        window.alert('Ви не авторизовані !');
      }
     }

     setCompleted() {
      const item = this.props.item;
      const url = 'http://localhost:8000/orderItems/'+item.id;
      let token = JSON.parse(sessionStorage.getItem('token'));
      if(token){
        const config = {
            headers: {
               "Authorization" : token.tokenType+' '+token.accessToken,
            },  
        }
        item.isCompleted = true;
        item.completed = true;
        item.user = this.props.user;
        axios
        .put(url, item, config)
        .then((res) => {
          this.setState({ message : res.data.message});
          this.props.onDelete(item);
        })
        .catch(err => this.setState({ message :  err.response.data.message}));
      }else{
        window.alert('Ви не авторизовані !');
      }
     }

     addToBasket() {
      const item = this.props.item;
      const url = 'http://localhost:8000/orderItems';
      let token = JSON.parse(sessionStorage.getItem('token'));
      if(token){
        const config = {
            headers: {
               "Authorization" : token.tokenType+' '+token.accessToken,
            },  
        }
        const orderItem = {
          user:this.state.user,
          product:item, 
        }
        axios
        .post(url, orderItem, config)
        .then((res) => {
          this.setState({ message : res.data.message});
        })
        .catch(err => this.setState({ message :  err.response.message}));
      }else{
        window.alert('Ви не авторизовані !');
      }
     }

    getDeleteButton() {
        if(!this.props.item.isCompleted || this.props.isAdmin){
          return(
            <>
            <div className="buttons">
              <div className="button">
              <button onClick={this.deleteThisItem}><span >🗑️</span>Видалити</button>
              </div>
            </div>       
            </>
          )
        }
    }

    getCloseButton() {
      if(!this.props.item.isCompleted){
        return(
          <>
          <div className="buttons">
            <div className="button">
            <button onClick={this.setCompleted}><span >🤪 </span>Оплатити</button>
            </div>
          </div>       
          </>
        )
      }
  }

   getDate(){
      let thisDate = new Date();
      thisDate.setTime(this.props.time);
      let date = thisDate.getDate()+'.'
      +thisDate.getMonth()+'.'+
      thisDate.getFullYear()+' '+
      thisDate.getHours()+':'+thisDate.getMinutes()+'';
      return date;
   }

   componentDidMount(){
    const url = 'http://localhost:8000/orderItems/'+this.props.item.id+'/user';
      let token = JSON.parse(sessionStorage.getItem('token'));
      if(token){
        const config = {
            headers: {
               "Authorization" : token.tokenType+' '+token.accessToken,
            },  
        }
        axios
        .get(url, config)
        .then((res) => {
          this.setState({ phone : res.data.phone, name : res.data.name});
        })
        .catch(err => this.setState({ message :  err.response.message}));
      }
   }
    render()
    {
        return (
            <article className="col1 flex column">
                <Link to={"/products/"+this.props.item.id}>
                    <div className="image">
                        <img src={'http://localhost:8000/products/images/'+this.props.item.product.id} alt={this.props.title}></img>
                    </div>
                    <h3>{this.props.title}</h3>
                </Link>
                <i>{this.props.description}</i>
                <b>{this.props.cost}</b>
                <i>{this.getDate()}</i>
                <i>{this.state.name+' '+this.state.phone}</i>
                <br></br>
                {this.getDeleteButton()}
                {this.getCloseButton()}
                <div className="button">
                </div>
            </article>

    );
}
}

export default OrderItem;
