export interface SnackbarState {
    message: string;
    severity: 'success' | 'error' | 'info' | 'warning';
    isOpen: boolean;
}

export interface UserState {
    name: string;
}

export interface AlertState {
    isOpen: boolean;
    message: string;
    confirmAction: () => void;
    cancelAction: () => void;
    confirmButtonString: string;
    cancelButtonString: string;
}
export interface CourseProgramState {
    isComeFromDetails: boolean;
}

export interface AppState {
    user: UserState;
    featureToggles: any;
    snackbar: SnackbarState;
    alert: AlertState;
    courseProgram: CourseProgramState;
}
