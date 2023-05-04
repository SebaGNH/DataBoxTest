import '@testing-library/jest-dom';
import { render, waitFor } from '@testing-library/react';
import Navbar from './Navbar';
import { BrowserRouter as Router } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import { AppContext } from '../../../App.context';
import { STATE_LOGIN } from '../../../utils/constants';

describe('<NavBar />', () => {
    it('El componente se renderiza correctamente', async () => {
        const dispatch = jest.fn();
        const { getByText } = render(
            <QueryClientProvider client={new QueryClient()}>
                <Router>
                    <AppContext.Provider value={{ state: STATE_LOGIN, dispatch }}>
                        <Navbar></Navbar>
                    </AppContext.Provider>
                </Router>
            </QueryClientProvider>
        );
        await waitFor(() => getByText('titles.navbar'));
    });
});
