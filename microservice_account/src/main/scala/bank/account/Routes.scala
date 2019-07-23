package bank.account

import bank.account.model.{AccountFillRequest, AccountTransaction}

object Routes {

  import bank.account.controller.AccountController
  import sparrow.account.model.{AccountFillRequest, AccountTransaction}
  private lazy val accountController = AccountController()

  final val balanceAccount: Endpoint[Option[AccountTransaction]] =
    get("balance" :: path[String]) {req: String =>
      for {
        r <- accountController.balanceAccount(req)
      } yield Ok(r)
    }

  final val fillAccount: Endpoint[AccountTransaction] =
    post("account" :: jsonBody[AccountFillRequest]) {req: AccountFillRequest =>
      for {
        r <- accountController.fillAccount(req.uuid, req.amount)
      } yield r match {
        case Right(a) => Ok(a)
        case Left(m) => BadRequest(m)
      }
    }
}
