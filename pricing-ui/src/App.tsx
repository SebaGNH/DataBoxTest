import React, { useEffect, useReducer, useState } from 'react';
import './styles/styles.scss';
import API from './services/api.service';
import { BrowserRouter as Router } from 'react-router-dom';
import { AppContext, initialState } from './App.context';
import { createTheme, ThemeProvider, Snackbar, Alert } from '@mui/material';
import { themeOptions } from './theme';
import { QueryClient, QueryClientProvider } from 'react-query';
import appReducer from './reducers/app.reducer';
import AppRoutes from './routes/App.routes';
import { appReducerCases } from './reducers/types';
import { ROLES } from './utils/constants';

const getRoles = (user: any = {}) => {
    const authorities = user && user.authorities ? user.authorities : [];
    const isValidUser = ROLES.some((e) => authorities.includes(e));
    return { authorities, isValidUser };
};

const queryClient = new QueryClient();

const App = () => {
    const [appData, setAppData] = useState<any>({
        isLoading: true
    });
    const [data, dispatch] = useReducer(appReducer, initialState);
    const theme = createTheme(themeOptions);

    useEffect(() => {
        initApp();
    }, []);

    const initApp = async () => {
        setAppData({ isLoading: true });
        const newAppData = await API.initApp();
        const isLogout = window.location.pathname.includes('logout');
        if (!isLogout && !newAppData.user) API.goToLogin();

        const userRoles = getRoles(newAppData.user);
        dispatch({
            type: appReducerCases.setUser,
            payload: newAppData.user
        });
        setAppData({
            ...newAppData,
            isLoading: false,
            userRoles: userRoles.authorities,
            isValidUser: userRoles.isValidUser
        });
    };
    console.log(appData);

    return (
        <ThemeProvider theme={theme}>
            <QueryClientProvider client={queryClient}>
                <Router>
                    <AppContext.Provider
                        value={{
                            state: data,
                            dispatch
                        }}
                    >
                        <div className="Home__Content">
                            <AppRoutes />
                        </div>
                        <Snackbar
                            open={data.snackbar?.isOpen}
                            autoHideDuration={8000}
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'center'
                            }}
                            onClose={() => {
                                dispatch({
                                    type: appReducerCases.closeSnackbar,
                                    payload: false
                                });
                            }}
                        >
                            <Alert severity={data.snackbar?.severity} sx={{ width: '100%', textAlign: 'center' }}>
                                {data.snackbar?.message}
                            </Alert>
                        </Snackbar>
                    </AppContext.Provider>
                </Router>
            </QueryClientProvider>
        </ThemeProvider>
    );
};

export default App;
