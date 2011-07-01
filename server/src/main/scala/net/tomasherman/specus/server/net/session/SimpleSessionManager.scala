package net.tomasherman.specus.server.net.session

import org.jboss.netty.channel.Channel
import collection.mutable.Map
import net.tomasherman.specus.server.api.net.packet.Packet
import net.tomasherman.specus.server.api.net.session.{Session, SessionID, SessionManager}

/**
 * This file is part of Specus.
 *
 * Specus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Specus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *
 * You should have received a copy of the GNU General Public License
 * along with Specus.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
class SimpleSessionManager extends SessionManager {
  private val sessions = Map[SessionID,Session]()
  private var lastId = 0;

  def createNewSession(channel: Channel) = {
    lastId += 1
    val sid = new IntSessionID(lastId)
    sessions(sid) = new NettySession(sid,channel)
    sid
  }
  def closeSession(id: SessionID) {
    sessions(id).close()
    sessions - id
  }

  def broadcast(data: Packet) {
    sessions.values.foreach(s => writeToSession(s,data))
  }

  def writeTo(id: SessionID, data:Packet) {
    sessions(id).write(data)
  }

  private def writeToSession(session:Session, data:Packet) {
    session.write(data)
  }
}