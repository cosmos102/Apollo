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
import apl.Alias;
import apl.AplException;
import apl.Attachment;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static apl.http.JSONResponses.INCORRECT_ALIAS_OWNER;


public final class DeleteAlias extends CreateTransaction {

    private static class DeleteAliasHolder {
        private static final DeleteAlias INSTANCE = new DeleteAlias();
    }

    public static DeleteAlias getInstance() {
        return DeleteAliasHolder.INSTANCE;
    }

    private DeleteAlias() {
        super(new APITag[] {APITag.ALIASES, APITag.CREATE_TRANSACTION}, "alias", "aliasName");
    }

    @Override
    protected JSONStreamAware processRequest(final HttpServletRequest req) throws AplException {
        final Alias alias = ParameterParser.getAlias(req);
        final Account owner = ParameterParser.getSenderAccount(req);

        if (alias.getAccountId() != owner.getId()) {
            return INCORRECT_ALIAS_OWNER;
        }

        final Attachment attachment = new Attachment.MessagingAliasDelete(alias.getAliasName());
        return createTransaction(req, owner, attachment);
    }
}
