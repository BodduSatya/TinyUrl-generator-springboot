import React from 'react';
import './App.css';
const Footer = () => {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="footer">
            <p>&copy; {currentYear} open to all. All Rights Reserved.</p>
        </footer>
    );
};

export default Footer;
