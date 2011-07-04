package net.tomasherman.specus.server.net

import org.specs2.mutable.Specification
import net.tomasherman.specus.server.api.net.packet.Packet
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.specs2.specification.Scope

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
class TestPacket extends Packet

class TestCodec extends EncodingCodec[TestPacket](0x01.toByte,classOf[TestPacket]){
  def decode(buffer: ChannelBuffer) = null //insignificant

  protected def encodeDataToBuffer(packet: TestPacket, buffer: ChannelBuffer) {
    buffer.writeBytes(Array[Byte](0x00,0x01,0x02))
  }

  protected def createChannelBuffer = ChannelBuffers.dynamicBuffer()
}
trait EncodingCodecScope extends Scope{
  val codec:EncodingCodec[TestPacket] = new TestCodec
}

class EncodingCodecSpec extends Specification{
  "EncodingCodec" should {
    "encode data properlny" in new EncodingCodecScope {
      codec.encode(new TestPacket).array().toList.splitAt(4)._1 must_== List(0x01,0x00,0x01,0x02)
      codec.encode(new TestPacket).array().toList.splitAt(4)._2.foldLeft(0)(_ + _) must_== 0
    }
  }
}