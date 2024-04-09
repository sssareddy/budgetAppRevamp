package com.budget.budgetRevamp.model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PerticularsResponse extends GenericResponse {
private List<String> itemsList;
private List<String> categoryList;
private List<String> purchaseModeList;
private List<String> paymentModeList;
private List<PerticularsEntity> perticulasList;
}
