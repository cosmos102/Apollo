/*
 * Copyright © 2013-2016 The Nxt Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 * Copyright © 2017-2018 Apollo Foundation
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida IP B.V.,
 * no part of the Nxt software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package apl.http;

import apl.Account;
import apl.Attachment;
import apl.Constants;
import apl.DigitalGoodsStore;
import apl.AplException;
import apl.util.Convert;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static apl.http.JSONResponses.DUPLICATE_REFUND;
import static apl.http.JSONResponses.GOODS_NOT_DELIVERED;
import static apl.http.JSONResponses.INCORRECT_DGS_REFUND;
import static apl.http.JSONResponses.INCORRECT_PURCHASE;

public final class DGSRefund extends CreateTransaction {

    private static class DGSRefundHolder {
        private static final DGSRefund INSTANCE = new DGSRefund();
    }

    public static DGSRefund getInstance() {
        return DGSRefundHolder.INSTANCE;
    }

    private DGSRefund() {
        super(new APITag[] {APITag.DGS, APITag.CREATE_TRANSACTION},
                "purchase", "refundATM");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws AplException {

        Account sellerAccount = ParameterParser.getSenderAccount(req);
        DigitalGoodsStore.Purchase purchase = ParameterParser.getPurchase(req);
        if (sellerAccount.getId() != purchase.getSellerId()) {
            return INCORRECT_PURCHASE;
        }
        if (purchase.getRefundNote() != null) {
            return DUPLICATE_REFUND;
        }
        if (purchase.getEncryptedGoods() == null) {
            return GOODS_NOT_DELIVERED;
        }

        String refundValueATM = Convert.emptyToNull(req.getParameter("refundATM"));
        long refundATM = 0;
        try {
            if (refundValueATM != null) {
                refundATM = Long.parseLong(refundValueATM);
            }
        } catch (RuntimeException e) {
            return INCORRECT_DGS_REFUND;
        }
        if (refundATM < 0 || refundATM > Constants.MAX_BALANCE_ATM) {
            return INCORRECT_DGS_REFUND;
        }

        Account buyerAccount = Account.getAccount(purchase.getBuyerId());

        Attachment attachment = new Attachment.DigitalGoodsRefund(purchase.getId(), refundATM);
        return createTransaction(req, sellerAccount, buyerAccount.getId(), 0, attachment);

    }

}
