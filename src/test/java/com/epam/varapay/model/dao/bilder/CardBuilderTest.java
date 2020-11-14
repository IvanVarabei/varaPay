package com.epam.varapay.model.dao.bilder;

import com.epam.varapay.model.dao.builder.IdentifiableBuilder;
import com.epam.varapay.model.dao.builder.impl.CardBuilder;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.Card;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class CardBuilderTest {
    @Mock
    private ResultSet resultSet;
    @Mock
    private IdentifiableBuilder<Account> accountBuilder;
    @Mock
    private Account account;
    @InjectMocks
    private CardBuilder cardBuilder = new CardBuilder();

    @BeforeMethod
    public void setup() throws SQLException {
        initMocks(this);
        when(resultSet.getLong("card_id")).thenReturn(1L);
        when(resultSet.getString("card_number")).thenReturn("1111222233334444");
        when(resultSet.getString("valid_thru")).thenReturn("2023-10-23");
        when(resultSet.getString("cvc")).thenReturn("123");
        when(accountBuilder.build(resultSet)).thenReturn(account);
    }

    @Test
    public void build() throws SQLException {
        Card expected = new Card();
        expected.setAccount(account);
        expected.setId(1L);
        expected.setCardNumber("1111222233334444");
        expected.setValidThru(LocalDate.parse("2023-10-23"));
        expected.setCvc("123");

        Card actual = cardBuilder.build(resultSet);

        assertEquals(actual, expected);
    }
}
