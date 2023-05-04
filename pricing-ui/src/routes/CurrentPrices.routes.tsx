import * as React from 'react';
import { Route } from 'react-router-dom';
import CurrentPrices from '../components/modules/current-prices/current-prices';

export default [
    <Route key="current-prices" path="/current-prices">
        <Route path="" element={<CurrentPrices />} />
    </Route>
];
