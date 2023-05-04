import { AppState } from '../models/appContext.model';
import { appReducerCases } from './types';

const appReducer = (state: AppState, action: { type: string; payload: any }) => {
    switch (action.type) {
        case appReducerCases.setUser:
            return {
                ...state,
                user: action.payload
            };
        case appReducerCases.setFeaturetoggles:
            return {
                ...state,
                featureToggles: action.payload
            };
        case appReducerCases.openSnackbar:
            return {
                ...state,
                snackbar: {
                    ...state.snackbar,
                    isOpen: true
                }
            };
        case appReducerCases.closeSnackbar:
            return {
                ...state,
                snackbar: {
                    ...state.snackbar,
                    isOpen: false,
                    message: ''
                }
            };
        case appReducerCases.setSnackbarMessage:
            return {
                ...state,
                snackbar: {
                    ...state.snackbar,
                    message: action.payload
                }
            };
        case appReducerCases.setSnackbarSeverity:
            return {
                ...state,
                snackbar: {
                    ...state.snackbar,
                    severity: action.payload
                }
            };
        case appReducerCases.closeAlert:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    isOpen: false,
                    message: '',
                    confirmAction: () => {},
                    cancelAction: () => {},
                    confirmButtonString: 'buttons.confirm',
                    cancelButtonString: 'buttons.cancel'
                }
            };
        case appReducerCases.openAlert:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    message: action.payload,
                    isOpen: true
                }
            };
        case appReducerCases.setConfirmButtonString:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    confirmButtonString: action.payload
                }
            };
        case appReducerCases.setCancelButtonString:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    cancelButtonString: action.payload
                }
            };
        case appReducerCases.resetButtonStrings:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    confirmButtonString: 'buttons.confirm',
                    cancelButtonString: 'buttons.cancel'
                }
            };
        case appReducerCases.setAlertConfirmAction:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    confirmAction: action.payload
                }
            };
        case appReducerCases.setAlertCancelAction:
            return {
                ...state,
                alert: {
                    ...state.alert,
                    cancelAction: action.payload
                }
            };
        case appReducerCases.setCourseProgramAction:
            return {
                ...state,
                courseProgram: action.payload
            };
        default:
            return state;
    }
};

export default appReducer;
