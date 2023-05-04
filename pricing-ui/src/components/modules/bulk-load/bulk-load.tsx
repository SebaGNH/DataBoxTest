import React, { useState, useContext } from 'react';
import { Box, TextField, Typography, Button } from '@mui/material';
import { useTranslation } from 'react-i18next';
import './bulk-load.scss';
import Toolbar from '../../layout/Toolbar/Toolbar';
import DropzoneFile from './dropzone-file/dropzone-file';
import { bulkLoadService } from '../../../services/bulk-load.service';
import LinearProgress from '@mui/material/LinearProgress';
import TableBulkLoad from './table-bulk-load/table-bulk-load';
import TableErrors from './table-errors/table-errors';
import { useQuery } from 'react-query';
import { AppContext } from '../../../App.context';
import { appReducerCases } from '../../../reducers/types';
import image from '../../../assets/img/round-warning-24px.svg';

const BulkLoad = () => {
    const { t } = useTranslation();
    const appContext = useContext(AppContext);
    const [file, setFile] = useState<any>(null);
    const [fileName, setFileName] = useState<string>('');
    const [isLoading, setLoading] = useState<boolean>(false);
    const [showErrors, setShowErrors] = useState<boolean>(false);
    const [errorsData, setErrorsData] = useState([]);

    const { data: fileData, refetch } = useQuery(
        ['filesCsv'],
        async () => {
            const data = await bulkLoadService.getRecentLots();
            return data;
        },
        { keepPreviousData: true, refetchOnWindowFocus: false }
    );

    const handleUploadFile = async () => {
        setLoading(true);
        try {
            const { data } = await bulkLoadService.fileUpload(file);
            const isEmpty = Object.keys(data).length === 0;
            if (isEmpty) {
                setShowErrors(false);
                refetch();
                appContext.dispatch({
                    type: appReducerCases.setSnackbarMessage,
                    payload: '¡Archivo subido exitosamente!'
                });
                appContext.dispatch({
                    type: appReducerCases.setSnackbarSeverity,
                    payload: 'success'
                });
                appContext.dispatch({
                    type: appReducerCases.openSnackbar,
                    payload: true
                });
            } else {
                const mapTickets = new Map(Object.entries(data));
                let arrayValues: any = [];
                mapTickets.forEach((value, key, map) => {
                    arrayValues = arrayValues.concat(value);
                });
                console.log(arrayValues);
                setErrorsData(arrayValues);
                setShowErrors(true);
            }
        } catch (error: any) {
            const message = error.response?.data ? error.response.data.message : '¡Hubo un error al intentar subir el archivo!';
            appContext.dispatch({
                type: appReducerCases.setSnackbarMessage,
                payload: message
            });
            appContext.dispatch({
                type: appReducerCases.setSnackbarSeverity,
                payload: 'error'
            });
            appContext.dispatch({
                type: appReducerCases.openSnackbar,
                payload: true
            });
        } finally {
            setFile(null);
            setFileName('');
            setLoading(false);
        }
    };

    const setFileUpload = (file: any) => {
        setFile(file);
        setFileName(file?.name);
    };

    return (
        <>
            <div className="layout__toolbar">
                <Toolbar />
            </div>
            <div className="container">
                <Box className="content_bulk_load">
                    <Typography className="module_title" data-testid="module-title" gutterBottom>
                        {t('titles.bulkLoad')}
                    </Typography>
                    <Typography className="module_subtitle1">{t('subtitles.subtitleBulkLoad')}</Typography>
                </Box>
                <Box sx={{ display: 'flex', flexWrap: 'wrap', alignContent: 'flex-start', alignItems: 'baseline', width: '50%' }}>
                    <TextField size="small" sx={{ m: 1, width: '60%' }} disabled={true} value={fileName} />
                    <Button variant="contained" component="label" onClick={handleUploadFile} disabled={!file}>
                        {t('buttons.uploadFile')}
                    </Button>
                </Box>
                <div className="mt-2">
                    <DropzoneFile setFile={setFileUpload} />
                </div>
                {isLoading && (
                    <>
                        <h4 className="module_title">Subiendo Archivo</h4>
                        <div className="content_progress_bar">
                            <div className="items-space-between">
                                <p>{fileName}</p>
                                <p>Validando archivo</p>
                            </div>
                        </div>
                        <LinearProgress color="success" />
                    </>
                )}
                {!showErrors && !isLoading && <TableBulkLoad fileData={fileData} />}
                {showErrors && !isLoading && (
                    <>
                        <h4 className="module_title">Archivo subido</h4>
                        <div className="content_progress_bar">
                            <div className="items-space-between">
                                <p>{fileName}</p>
                                <span style={{ display: 'flex' }}>
                                    <img src={image} />
                                    <p>Por favor, verificá los errores que tiene el archivo para subirlo nuevamente.</p>
                                </span>
                            </div>
                        </div>
                        <TableErrors errosData={errorsData} />
                    </>
                )}
            </div>
        </>
    );
};

export default BulkLoad;
