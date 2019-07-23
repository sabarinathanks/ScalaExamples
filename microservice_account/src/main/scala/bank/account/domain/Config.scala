package bank.account.domain

import com.typesafe.config.ConfigFactory
import bank.account.model.ConfigProperty.ServerConfig

trait Config {

  private[this] lazy val loadConf = ConfigFactory.load().getConfig("sparrow.account.server")

  lazy val serverConf = ServerConfig(loadConf.getString("name"),
    loadConf.getString("host"),
    loadConf.getInt("port"),
    loadConf.getInt("maxConcurrentRequests"),
    loadConf.getInt("maxWaiters"))
}
