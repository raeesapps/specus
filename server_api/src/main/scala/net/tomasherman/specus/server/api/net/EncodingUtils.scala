package net.tomasherman.specus.server.api.net

import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.util.CharsetUtil
import java.nio.charset.Charset
import annotation.tailrec


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

/** Set of functions encoding stuff to ChannelBuffers. Write index
  * is being updated properly while Writing therefore one can chain these
  * methods. */
object EncodingUtils {
  
  def encodeByte(v: Byte, b: ChannelBuffer) {
    b.writeByte(v)
  }

  def encodeShort(v: Short, b: ChannelBuffer) {
    b.writeShort(v)
  }

  def encodeInt(v: Int, b: ChannelBuffer) {
    b.writeInt(v)
  }

  def encodeLong(v: Long, b: ChannelBuffer) {
    b.writeLong(v)
  }

  def encodeFloat(v: Float, b: ChannelBuffer) {
    b.writeFloat(v)
  }

  def encodeDouble(v: Double, b: ChannelBuffer) {
    b.writeDouble(v)
  }

  def encodeBoolean(v: Boolean, b: ChannelBuffer) {
    v match {
      case true => b.writeByte(0x01)
      case false => b.writeByte(0x00)
    }
  }

  /** Encodes UTF-8 String */
  def encodeString8(v: String, b: ChannelBuffer) {
    encodeString(v, b, CharsetUtil.UTF_8)
  }
  /** Encodes UTF-16 String */
  def encodeString16(v: String, b: ChannelBuffer) {
    encodeString(v, b, CharsetUtil.UTF_16BE)
  }

  private def encodeString(v: String, b: ChannelBuffer, charset: Charset) {
    val arr = v.getBytes(charset)
    b.writeShort(arr.length)
    b.writeBytes(arr)
  }

  def encodeMetadata(v: List[Any], b: ChannelBuffer) {
    encodeMetadataRec(v, b)
    b.writeByte(0x7F)
  }

  @tailrec
  def encodeMetadataRec(values: List[Any], b: ChannelBuffer) {
    values match {
      case Nil => {}
      case x :: xs => {
        x match {
          case v: Byte => b.writeByte(0x00 << 5); encodeByte(v, b)
          case v: Short => b.writeByte(0x01 << 5); encodeShort(v, b)
          case v: Int => b.writeByte(0x02 << 5); encodeInt(v, b)
          case v: Long => b.writeByte(0x03 << 5); encodeLong(v, b)
          case v: String => b.writeByte(0x04 << 5); encodeString16(v, b)
          case (x: Short, y: Byte, z: Short) => b.writeByte(0x05 << 5); encodeShort(x, b); encodeByte(y, b); encodeShort(z, b)
          case (x: Int, y: Int, z: Int) => b.writeByte(0x06 << 5); encodeInt(x, b); encodeInt(y, b); encodeInt(z, b)
        }
      }
      encodeMetadataRec(xs, b)
    }
  }
}