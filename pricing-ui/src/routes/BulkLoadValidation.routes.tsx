import * as React from 'react';
import { Route } from 'react-router-dom';
import BulkLoadValidation from '../components/modules/bulk-load-validation/bulk-load-validation';
import LogDisplay from '../components/modules/bulk-load-validation/log-display/log-display';

export default [
    <Route key="bulk-load-validation" path="/bulk-load-validation">
        <Route path="" element={<BulkLoadValidation />} />
        <Route path=":csvId" element={<BulkLoadValidation />} />
        <Route path="logDisplay/:csvId" element={<LogDisplay />} />
        <Route path=":csvId/logDisplay/:csvId" element={<LogDisplay />} />
    </Route>
];
