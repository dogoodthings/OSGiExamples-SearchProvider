package org.dogoodthings.ectr.osgi.search.material;

import java.util.ArrayList;
import java.util.List;

import org.dogoodthings.ectr.osgi.search.PluginProcessRfcSearch;
import org.dogoodthings.ectr.osgi.search.provider.DefaultSearchHit;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.interfaces.rfc.RfcTable;
import com.dscsag.plm.spi.interfaces.search.SearchHit;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

/*
================================================================================
#39 /DSCSAG/MAT_GETLIST2
================================================================================

 INPUT -------------------------------------------------------------------------


    STRUCTURE: SELECT_MATDATA (/DSCSAG/MATDATA_EXT)
           | MATERIAL | DESCRIPTION | STATUS | LABOR_OFFICE | GENITEMCAT | PROD_HIER |
           |          |             |        |              |            |           |

     | NAME                                 | VALUE                                |
     | GETCLASSIFICATION                    |                                      |
     | IV_CLASSNAME                         |                                      |
     | IV_CLASSTYPE                         |                                      |
     | IV_CLASS_SUB_OBJECTS_GET             |                                      |
     | IV_CLIENT_VERSION                    | 4050                                 |
     | IV_CONV_RANGE2INTERN                 | X                                    |
     | IV_DSC_CONV_ID                       | 2a4f863d-4077-4b3f-af48-95c4c1846c23 |
     | IV_ESH_ACTIVE                        |                                      |
     | IV_ESH_SEARCH_TERM                   |                                      |
     | IV_GET_MARA_DETAILS                  | X                                    |
     | IV_LANG_ISO                          | EN                                   |
     | IV_MAXCLASSHITS                      | 99990                                |
     | IV_MAXDETAILS                        | 0                                    |
     | IV_MAXMATHITS                        | 99999                                |
     | IV_MAXROWS                           | 101                                  |
     | IV_PACKAGE_SIZE                      | 5000                                 |
     | IV_RECURSIVE                         |                                      |




 */

public abstract class PluginProcessSearchMaterial extends PluginProcessRfcSearch
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = new RfcCallBuilder("/DSCSAG/MAT_GETLIST2");
    rfcCallBuilder.setInputParameter("IV_GET_MARA_DETAILS"," ");
    rfcCallBuilder.setInputParameter("IV_MAXDETAILS","0");
    rfcCallBuilder.setInputParameter("IV_MAXMATHITS",String.valueOf(searchQuery.getMaxHits()));
    rfcCallBuilder.setInputParameter("IV_MAXROWS",String.valueOf(searchQuery.getMaxHits()));
    rfcCallBuilder.setInputParameter("IV_RECURSIVE"," ");
    return rfcCallBuilder;
  }

  @Override
  protected SearchResult createResult(RfcResult rfcResult)
  {
    List<SearchHit> searchResult = new ArrayList<>();
    RfcTable mats = rfcResult.getTable("OUT_MATNUMS");
    for(int i=0;i<mats.getRowCount();i++)
      searchResult.add(new DefaultSearchHit(new PlmObjectKey("MARA", mats.getRow(i).getFieldValue("MATERIAL")),1.0f));
    return ()->searchResult;
  }

}