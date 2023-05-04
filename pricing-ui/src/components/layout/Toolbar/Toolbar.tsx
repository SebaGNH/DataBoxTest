import React from 'react';
import { useTranslation } from 'react-i18next';
import { Drawer, List, ListItem, ListItemIcon, Tooltip } from '@mui/material';
import AssignmentIcon from '@mui/icons-material/Assignment';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import DehazeIcon from '@mui/icons-material/Dehaze';
import './toolbar.css';
import { NavLink } from 'react-router-dom';

const Toolbar = () => {
    const { t } = useTranslation();

    return (
        <div className="Sidebar__Container">
            <Drawer className="Sidebar__Drawer" variant="permanent">
                <List className="sidebar__list" id="rmt-toolbar">
                    <ListItem key="1" button className="Sidebar__Drawer__List__Item" component={NavLink} to="/current-prices" tabIndex={-1}>
                        <Tooltip title={t('titles.prices')}>
                            <ListItemIcon id="rmt-toolbar-programs" className="Sidebar__Drawer__List__Item__Icon">
                                <AssignmentIcon />
                            </ListItemIcon>
                        </Tooltip>
                    </ListItem>
                    <ListItem key="2" button className="Sidebar__Drawer__List__Item" component={NavLink} to="/bulk-load" tabIndex={-1}>
                        <Tooltip title={t('titles.bulkLoad')}>
                            <ListItemIcon id="rmt-toolbar-courses" className="Sidebar__Drawer__List__Item__Icon">
                                <UploadFileIcon />
                            </ListItemIcon>
                        </Tooltip>
                    </ListItem>
                    <ListItem key="3" button className="Sidebar__Drawer__List__Item" component={NavLink} to="/bulk-load-validation" tabIndex={-1}>
                        <Tooltip title={t('titles.bulkLoadValidation')}>
                            <ListItemIcon id="rmt-toolbar-bulk-load-validation" className="Sidebar__Drawer__List__Item__Icon">
                                <DehazeIcon />
                            </ListItemIcon>
                        </Tooltip>
                    </ListItem>
                </List>
            </Drawer>
        </div>
    );
};

export default Toolbar;
