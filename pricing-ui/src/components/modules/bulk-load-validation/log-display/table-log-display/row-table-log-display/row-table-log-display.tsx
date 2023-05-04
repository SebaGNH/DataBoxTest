import * as React from 'react';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Box, IconButton, Chip, Table, TableBody, TableCell, TableRow, Collapse, Tooltip } from '@mui/material';
import { KeyboardArrowDown as KeyboardArrowDownIcon, KeyboardArrowUp as KeyboardArrowUpIcon } from '@mui/icons-material';
import Checkbox from '@mui/material/Checkbox';
import EditIcon from '@mui/icons-material/Edit';
import formatNumber from '../../../../../shared/FormatPrice';
import '../table-log-display.scss';
import EditLogDisplay from '../../edit-log-display/edit-log-display';

const RowTableLogDisplay = ({ item, handleItemSelected, handleCleanFilter }: any) => {
    const [isAccordionOpen, setIsAccordionOpen] = useState(false);
    const { t } = useTranslation();
    const [isOpen, setIsOpen] = useState(false);

    const handleEdit = () => {
        setIsOpen(true);
    };

    const closeModal = () => {
        handleCleanFilter();
        setIsOpen(false);
    };

    const color = item.idEstado === 2 ? 'error' : item.idEstado === 4 ? 'primary' : 'warning';

    return (
        <>
            <TableRow sx={{ boxShadow: 2 }} className="table__mainRow">
                <TableCell className="table__cellAux">
                    <Table>
                        <TableBody>
                            <TableRow className="table__log_Display_Body" hover={true} sx={{ '& > *': { borderBottom: 'unset' } }}>
                                <TableCell className="table__cell table__controlCell">
                                    <Checkbox checked={item?.checked} onChange={(e) => handleItemSelected(e, item)} />
                                </TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.csvId}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.descripcionCarrera}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.cau}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.tipoModalidad}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.periodoAcademico}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.typeTicket}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.turnoCursadoDesc}</TableCell>
                                <TableCell className="table__courseProgramCellBody">
                                    <Chip label={item.estado} color={color} />
                                </TableCell>
                                <TableCell className="table__courseProgramCellBody">{item.tipoALumno}</TableCell>
                                <TableCell className="table__courseProgramCellBody">{formatNumber(item.precio)}</TableCell>
                                <TableCell className="table__cell table__controlCell">
                                    <Tooltip style={{ paddingInline: '5px' }} title={t('tooltips.editLog')}>
                                        <div>
                                            <IconButton aria-label="expand row" size="small" id="rmt-syllabusEdit" onClick={handleEdit}>
                                                <EditIcon />
                                            </IconButton>
                                        </div>
                                    </Tooltip>
                                </TableCell>
                                <TableCell className="table__cell table__controlCell">
                                    <IconButton aria-label="rmt-courseExpand" size="small" id="rmt-courseExpand">
                                        {isAccordionOpen ? (
                                            <KeyboardArrowUpIcon onClick={() => setIsAccordionOpen(!isAccordionOpen)} />
                                        ) : (
                                            <KeyboardArrowDownIcon onClick={() => setIsAccordionOpen(!isAccordionOpen)} />
                                        )}
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                            <TableRow style={{ width: '100%', display: 'grid' }}>
                                <TableCell style={{ paddingBottom: 0, paddingTop: 0, paddingLeft: 0, paddingRight: 0 }} colSpan={6}>
                                    <Collapse in={isAccordionOpen} timeout="auto" unmountOnExit>
                                        <Box sx={{ margin: 0 }}>
                                            <Table size="small" sx={{ borderSpacing: '0px 0px !important' }}>
                                                <TableBody>
                                                    <TableRow>
                                                        <div className="accordion">
                                                            <div className="accordion_content">
                                                                <div className="accordion_item">
                                                                    <p className="title-detail-classroom">{t('tableCell.registrationData')}</p>
                                                                    <div className="row">
                                                                        <div className="col col-35">
                                                                            <p className="subtitle-detail-1">{t('tableCell.date')}</p>
                                                                            <div className="mt-1">
                                                                                {new Date(item.fechaCarga).toLocaleDateString()}
                                                                            </div>
                                                                        </div>
                                                                        <div className="col col-35">
                                                                            <p className="subtitle-detail-1">{t('tableCell.idArticle')}</p>
                                                                            <div className="mt-1">{item.idArticle}</div>
                                                                        </div>
                                                                        <div className="col col-30">
                                                                            <p className="subtitle-detail-1">{t('tableCell.modalityDes')}</p>
                                                                            <div className="mt-1">{item.modality}</div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="row">
                                                                        <div className="col col-35">
                                                                            <p className="subtitle-detail-1">{t('tableCell.percentage')}</p>
                                                                            <div className="mt-1">{item.porcentaje}</div>
                                                                        </div>
                                                                        <div className="col col-35">
                                                                            <p className="subtitle-detail-1">{t('tableCell.numberSubjects')}</p>
                                                                            <div className="mt-1">{item.cantidadMaterias}</div>
                                                                        </div>
                                                                        <div className="col col-30">
                                                                            <p className="subtitle-detail-1">{t('tableCell.tariff')}</p>
                                                                            <div className="mt-1">{item.tipoArancel}</div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </Box>
                                    </Collapse>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableCell>
            </TableRow>
            <EditLogDisplay isOpen={isOpen} onCancel={closeModal} item={item} />
        </>
    );
};

export default RowTableLogDisplay;
