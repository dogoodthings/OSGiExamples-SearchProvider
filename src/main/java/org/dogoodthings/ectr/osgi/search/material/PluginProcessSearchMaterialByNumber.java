//*****************************************************************************
//
//                            Copyright (c) 2019
//                    DSC Software AG, Karlsruhe, Germany
//                            All rights reserved
//
//        The contents of this file is an unpublished work protected
//        under the copyright law of the Federal Republic of Germany
//
//        This software is proprietary to and embodies confidential
//        technology of DSC Software AG. Possession, use and copying
//        of the software and media is authorized only pursuant to a
//        valid written license from DSC Software AG. This copyright
//        statement must be visibly included in all copies.
//
//*****************************************************************************

//*****************************************************************************
//
// $ ($)
//
// filename : PluginProcessSearchMaterialByNumber.java
//
// contents : 
//
// created at : 29.08.2019
// created by : wt, DSC Software AG, Karlsruhe, Germany
//
//*****************************************************************************

package org.dogoodthings.ectr.osgi.search.material;

import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;
import com.dscsag.plm.spi.rfc.builder.RfcTableBuilder;

/*
 /DSCSAG/MAT_GETLIST2

 TABLE: IT_MATNRSELECTION (/DSCSAG/BAPIMATRAM) , 1 row(s)
 | SIGN | OPTION | MATNR_LOW | MATNR_HIGH |
 | I    | CP     | *816      |            |
 */
public class PluginProcessSearchMaterialByNumber extends PluginProcessSearchMaterial
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(String searchTerm, int maxHits)
  {
    RfcCallBuilder rfcCallBuilder = super.prepareRfcCallBuilder(searchTerm,maxHits);
    RfcTableBuilder rfcTableBuilder = new RfcTableBuilder("SIGN","OPTION","MATNR_LOW","MATNR_HIGH");
    rfcTableBuilder.addRow("I","CP","*"+searchTerm,"");
    rfcCallBuilder.addTable("IT_MATNRSELECTION",rfcTableBuilder.toRfcTable());
    return rfcCallBuilder;
  }
}
