import React from 'react';
import { Link } from "react-router-dom";
import Respond from './Respond';
import categories from './categories'

const axios = require('axios');

class Item extends React.Component {
    constructor(props) {
        super(props);
        let user = sessionStorage.getItem('user');
        user = user ? JSON.parse(user) : {};
        this.state = {
          item: {
            id: '',
            image:'',
            name:'',
            description:'',
            category: {
              name: '',
            },
          }
        };
        console.log(this.state.item.category.name)
        this.componentDidMount = this.componentDidMount.bind(this);

        this.render = this.render.bind(this);
        
    }

    componentDidMount() {
      axios.get('http://localhost:8000/products/' + this.props.match.params.id )
      .then(res => {
        this.setState({item: res.data});
      }).then(res=>res.json())
      .catch((err, res)=>{
        this.setState({err:err.message})
      });

    }

    render()
    {
        return (
           <>
            <section className="flex">
                <div className="col1 flex item">

                    <div className="image col2">
                        <img src={'http://localhost:8000/products/images/'+this.state.item.id} alt={this.state.item.name || ''}></img>
                    </div>
                    <div className="text col5">
                    <i>Категорія: {categories[this.state.item.category.name]|| ''}</i>
                        <h3>{this.state.item.name || ''}</h3>

                        <i>{this.state.item.description || ''}</i>
                        
                    </div>

                </div>
            </section>
            <div className="flex">
                <h1>
                    Відгуки покупців
                </h1>
            </div>
            <section className="flex column">
              
            </section>
           </>

        );

    }
}

export default Item;
