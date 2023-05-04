import React, { useContext } from 'react';
import { AppBar, Toolbar, Divider, IconButton } from '@mui/material';
import svgLogo from '../../../assets/s21.svg';
import './Navbar.css';
import { useTranslation } from 'react-i18next';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import { AppContext } from '../../../App.context';
import { Link } from 'react-router-dom';

const Navbar = () => {
    const { t } = useTranslation();
    const date = new Date().toLocaleDateString();
    const appContext = useContext(AppContext);
    return (
        <AppBar className="Navbar Navbar__Container" position="fixed" color="default" id="rmt-navBar">
            <Toolbar className="Navbar__Toolbar__Container">
                <Link to={'/'} className="Navbar__link">
                    <div className="Navbar__Logo">
                        <img src={svgLogo} alt="Siglo21" />
                    </div>
                    <span className="Navbar__AppName">{t('titles.navbar')}</span>
                </Link>
                <div className="Navbar__User">
                    {' '}
                    <span>Hola, {appContext?.state.user.name}</span>
                    <span>{date}</span>
                </div>
                <div className="">
                    <IconButton tabIndex={-1}>
                        <KeyboardArrowDownIcon></KeyboardArrowDownIcon>
                    </IconButton>
                </div>
                <Divider orientation="vertical" variant="middle" flexItem />
                <a href="" className="Navbar__Help">
                    {t('labels.help')}
                </a>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
