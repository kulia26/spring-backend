import React from 'react';
import { Link } from "react-router-dom";

class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            input: '',
        };
        this.handleChange= this.handleChange.bind(this);  
        this.handleClick= this.handleClick.bind(this);   
      }


      handleChange(event) {
        this.setState({ input: event.target.value });
      }
    
      handleClick() {
          if(this.state.input.length > 0){
            window.location.pathname = 'search/'+this.state.input;
          }
        
      }
    getLinks = () =>{
        if(this.props.login === false){
            return(
                <>
                <div className="col1 center mb-hidden">
                    <Link to="/login">
                        Вхід
                    </Link>
                </div>
                <div className="col1 center mb-hidden">
                    <Link to="/register">
                        Реєстрація
                    </Link>
                </div>
                </>
            )
        }else{
            const divStyle = {
                backgroundImage: 'url(' + 'http://localhost:8000/users/images/'+this.props.phone+'/'+ ')'
            }
            if(this.props.imageUrl){
                divStyle.backgroundImage = 'url('+this.props.imageUrl+ ')';
                
            }
            
            return(
                <>
                <div className="col1 center mb-hidden">
                <div className="user-image" style={divStyle} >
                    
                </div>
                    
                </div> 
                <div className="col1 center mb-hidden">
                    <button onClick={this.props.onLogOut}>
                        Вихід
                    </button>
                </div>
                 
                </>
            ) 
        }
    }
  render(){
      return (
        <header className="flex">
            <button className="mb-only mobile-menu mobile-menu-bars">
                <i className="fas fa-bars"></i>
            </button>
            <button className="mb-only mobile-menu mobile-menu-times" href="#">
                <i className="fas fa-times"></i>
            </button>
            {this.getLinks()}
            <div className="col3 center">
                <Link to="/index">
                    <h1 className="name">online shop</h1>
                </Link>
            </div>
            <div className="col1 center mb-hidden"><i className="fas fa-mobile-alt"></i>8 800 555 35 35</div>
            <div className="col1 center mb-hidden">
                    <input onChange={this.handleChange} name="string"></input>
                    <button onClick={this.handleClick}><i className="fas fa-search"></i></button>
            </div>
        </header>
    );
}
}

export default Header;
