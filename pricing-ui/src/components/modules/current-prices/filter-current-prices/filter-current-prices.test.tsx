import { render, RenderResult, waitFor } from '@testing-library/react';
import FilterCurrentPrices from './filter-current-prices';
import { currentPricesService } from '../../../../services/current-prices.service';
import { act } from 'react-dom/test-utils';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter as Router } from 'react-router-dom';
import { AppContext } from '../../../../App.context';
import { STATE_LOGIN } from '../../../../utils/constants';

jest.mock('../../../../services/current-prices.service', () => ({
    currentPricesService: {
        getCareers: jest.fn(),
        getModalities: jest.fn(),
        getCaus: jest.fn(),
        getPeriods: jest.fn(),
        getArancels: jest.fn(),
        getTickets: jest.fn()
    }
}));

jest.mock('@mui/x-date-pickers/internals/demo', () => ({
    __esModule: true,
    default: jest.fn(),
    DemoContainer: jest.fn(),
    DatePicker: jest.fn()
}));

describe('<FilterCurrentPrices />', () => {
    it('El modulo se renderiza correctamente', async () => {
        const mockCareers = jest.spyOn(currentPricesService, 'getCareers').mockImplementation(() => Promise.resolve([]));
        const mockModalities = jest.spyOn(currentPricesService, 'getModalities').mockImplementation(() => Promise.resolve([]));
        const mockCaus = jest.spyOn(currentPricesService, 'getCaus').mockImplementation(() => Promise.resolve([]));
        const mockPeriods = jest.spyOn(currentPricesService, 'getPeriods').mockImplementation(() => Promise.resolve([]));
        const mockArancels = jest.spyOn(currentPricesService, 'getArancels').mockImplementation(() => Promise.resolve([]));
        const mockTickets = jest.spyOn(currentPricesService, 'getTickets').mockImplementation(() => Promise.resolve([]));

        let renderOptions: RenderResult;
        await act(async () => {
            renderOptions = render(
                <QueryClientProvider client={new QueryClient()}>
                    <Router>
                        <AppContext.Provider value={{ state: STATE_LOGIN, dispatch: () => {} }}>
                            <FilterCurrentPrices handleFilters={() => {}}></FilterCurrentPrices>
                        </AppContext.Provider>
                    </Router>
                </QueryClientProvider>
            );
        });
        await waitFor(() => {
            expect(mockCareers).toBeCalled();
            expect(mockModalities).toBeCalled();
            expect(mockCaus).toBeCalled();
            expect(mockPeriods).toBeCalled();
            expect(mockArancels).toBeCalled();
            expect(mockTickets).toBeCalled();
        });
        await waitFor(() => {
            let button = renderOptions.getByText('buttons.search');
            expect(button).not.toBeDisabled();
            expect(renderOptions.container?.querySelector('#rmt-CsvId')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Career')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Cau')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Modality')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Period')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Arancel')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-Ticket')).toBeTruthy();
            expect(renderOptions.container?.querySelector('#rmt-StudentType')).toBeTruthy();
        });
    });
});
