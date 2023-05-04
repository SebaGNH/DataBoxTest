import * as React from 'react';
import { Route } from 'react-router-dom';
import BulkLoad from '../components/modules/bulk-load/bulk-load';

export default [
    <Route key="bulk-load" path="/bulk-load">
        <Route path="" element={<BulkLoad />} />
    </Route>
];
