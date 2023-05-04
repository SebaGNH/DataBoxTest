import axios from 'axios';

declare var env: any;

const urlBff = process.env.NODE_ENV === 'development' ? process.env.REACT_APP_PRICING_BFF_URL : env.getBaseUrl();

const baseURL = urlBff + '/v1';

const loginURL = urlBff + '/login';

export const axiosInstance = axios.create({
    baseURL,
    withCredentials: true,
    headers: {
        Accept: 'application/json'
    }
});

axiosInstance.interceptors.response.use(
    (response) => response,
    (err) => {
        const { response } = err;
        const isLogout = window.location.pathname.includes('logout');
        if (!isLogout && response && response.status === 401) {
            API.goToLogin();
        } else if (response && response.status >= 500) {
            // @TODO: error page
        }

        return Promise.reject(err);
    }
);
export class API {
    static async initApp() {
        let user = null;

        try {
            user = await API.getUser();
        } catch (e: any) {
            if (e.response && e.response.status === 403) {
                user = {
                    authorities: []
                };
            }
        }
        return { user };
    }

    static goToLogin() {
        window.location.replace(loginURL);
    }

    static async getUser() {
        const { data } = await axiosInstance.get('/user');
        return data;
    }
}

export default API;
