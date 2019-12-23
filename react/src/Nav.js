import React from 'react';
import { NavLink } from "react-router-dom";

function Nav() {
  return (
    <nav className="flex mb-hidden">
        <div className="col1"></div>
        <div className="col1 center">
            <NavLink to="/index" activeClassName="active">
                <h2>Головна</h2>
            </NavLink>
        </div>
        <div className="col1 center">
            <NavLink to="/about" activeClassName="active">
                <h2>Про сайт</h2>
            </NavLink>
        </div>
        <div className="col1 center">
            <NavLink to="/orders" activeClassName="active">
                <h2>Замовлення</h2>
            </NavLink>
        </div>
        <div className="col1"></div>
    </nav>
  );
}

export default Nav;
