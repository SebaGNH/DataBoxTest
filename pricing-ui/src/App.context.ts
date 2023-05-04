import React, { createContext } from 'react';
import { AppState } from './models/appContext.model';

export const initialState: AppState = {
    user: {
        name: ''
    },
    courseProgram: {
        isComeFromDetails: false
    },
    featureToggles: {},
    snackbar: {
        message: '',
        severity: 'success',
        isOpen: false
    },
    alert: {
        isOpen: false,
        message: '',
        confirmAction: () => {},
        cancelAction: () => {},
        confirmButtonString: 'buttons.confirm',
        cancelButtonString: 'buttons.cancel'
    }
};

export const AppContext = createContext<{
    state: any;
    dispatch: React.Dispatch<any>;
}>({} as any);
