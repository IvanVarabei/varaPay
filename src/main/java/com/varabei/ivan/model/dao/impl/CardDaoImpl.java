package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.dao.builder.impl.CardBuilder;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class CardDaoImpl extends GenericDao<Card> implements CardDao {
    private static final String FIND_CARDS_BY_ACCOUNT_ID =
            "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
                    "       balance, is_active, users.user_id, users.login,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
                    "    join accounts on cards.account_id = ? and cards.account_id = accounts.account_id\n" +
                    "    and cards.is_abandoned = false\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    join roles on users.role_id = roles.role_id";
    private static final String FIND_CARD_BY_ID = "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
            "       balance, is_active, users.user_id, users.login,users.email,\n" +
            "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
            "    join accounts on card_id = ? and cards.account_id = accounts.account_id\n" +
            "    join users on accounts.user_id = users.user_id\n" +
            "    join roles on users.role_id = roles.role_id";
    private static final String ABANDON_CARD = "update cards set is_abandoned = true where card_Id = ?";
    private static final String CREATE_CARD = "insert into cards (account_id) values (?)";

    public CardDaoImpl() {
        super(new CardBuilder());
    }

    @Override
    public void create(Long accountId) throws DaoException {
        executeUpdate(CREATE_CARD, accountId);
    }

    @Override
    public Optional<Card> findById(Long id) throws DaoException {
        return executeForSingleResult(FIND_CARD_BY_ID, id);
    }

    @Override
    public List<Card> findByAccountId(Long accountId) throws DaoException {
        return executeQuery(FIND_CARDS_BY_ACCOUNT_ID, accountId);
    }

    @Override
    public void delete(Long cardId) throws DaoException {
        executeUpdate(ABANDON_CARD, cardId);
    }

//    public static void main(String[] args) {
//        try {
//            URL queryUrl = new URL("https://api.livecoin.net/exchange/order_book?currencyPair=BTC/USD");
//            HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();
//            connection.setDoOutput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line + '\n');
//            }
//            System.out.println("before");
//            Double d = parseBitcoinCost(sb.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    static double parseBitcoinCost(String s) {
//        Pattern pattern = Pattern.compile("(?<=\"asks\":\\[\\[\").+?(?=\")");
//        Matcher matcher = pattern.matcher(s);
//        matcher.find();
//        return Double.parseDouble(matcher.group());
//    }
}
