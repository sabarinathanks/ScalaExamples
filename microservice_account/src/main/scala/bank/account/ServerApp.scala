package bank.account

import bank.account.domain.Config
import com.twitter.server.TwitterServer
import com.twitter.finagle.Http
import com.twitter.util.Await

class ServerImpl extends TwitterServer with Config {

  def run(): Unit = {
    val app = Http
      .server
      .withLabel(serverConf.name)
      .withAdmissionControl.concurrencyLimit(
      maxConcurrentRequests = serverConf.maxConcurrentRequests,
      maxWaiters = serverConf.maxWaiters
    ).serve(s"${serverConf.host}:${serverConf.port}",
      (Routes.balanceAccount :+: Routes.fillAccount).toService)

    onExit {
      app.close()
    }

    Await.ready(app)
  }
}

object ServerApp extends LogSupport with Config {

  def main(args: Array[String]): Unit = {
    val greeting = () => {s"*** Stating ${serverConf.name} ****\n"}
    val address = () => {s"Host: ${serverConf.host} Port: ${serverConf.port}"}

    info(s"${greeting()}${address()}")
    new ServerImpl().run()
  }
}
