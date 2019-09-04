package org.dogoodthings.ectr.osgi.search.provider;

import java.util.Arrays;
import java.util.List;

import org.dogoodthings.ectr.osgi.ECTRServiceHolder;

import com.dscsag.plm.spi.interfaces.search.SearchMode;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchTerm;

/**
 *
 */
public class DefaultSingleSearchQuery implements SearchQuery
{
  private int maxHits;
  private SearchTerm searchTerm;

  public DefaultSingleSearchQuery(int maxHits, String searchString)
  {
    this.maxHits = maxHits;
    searchTerm = createSearchTerm(searchString);
  }

  @Override
  public int getMaxHits()
  {
    return maxHits;
  }

  public static SearchTerm createSearchTerm(String searchText)
  {
    ECTRServiceHolder.getEctrService().getPlmLogger().trace("search text: '" + searchText + "'");
    SearchMode mode = SearchMode.PATTERN;
    if (searchText.startsWith("\"") && searchText.endsWith("\"") && searchText.length() > 2)
    {
      mode = SearchMode.EQUALS;
      searchText = searchText.substring(1, searchText.length() - 1);
    }
    final SearchMode finalMode = mode;
    final String fixedSearchText = searchText;
    ECTRServiceHolder.getEctrService().getPlmLogger().trace("fixed search text: '" + fixedSearchText + "'");
    return new SearchTerm()
    {
      @Override
      public String getText()
      {
        return fixedSearchText;
      }

      @Override
      public SearchMode getMode()
      {
        return finalMode;
      }
    };
  }

  @Override
  public List<SearchTerm> getTerms()
  {
    return Arrays.asList(searchTerm);
  }
}